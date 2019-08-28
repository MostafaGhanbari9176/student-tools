package ir.pepotec.app.awesomeapp.view.student.chat.messageList

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.chat.ChatMessageData
import ir.pepotec.app.awesomeapp.model.student.chat.ChatMessageDb
import ir.pepotec.app.awesomeapp.model.student.chat.FileMessageDb
import ir.pepotec.app.awesomeapp.presenter.student.ChatPresenter
import ir.pepotec.app.awesomeapp.view.file.ActivityFile
import ir.pepotec.app.awesomeapp.view.student.chat.ServiceChat
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_message_list.*
import java.io.File
import android.content.ActivityNotFoundException
import android.webkit.MimeTypeMap
import android.net.Uri
import ir.pepotec.app.awesomeapp.presenter.FilePresenter


class FragmentMessageList : MyFragment(), ServiceChat.ChatInterface, AdapterMessageList.AdapterMessageListEvent {

    val conn:ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            chatService = (service as ServiceChat.ChatBinder).getService().apply {
                user_id = this@FragmentMessageList.user_id
                listener = this@FragmentMessageList
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            chatService?.user_id = -1
            chatService?.listener = null
        }
    }
    var user_id = -1
    var fPath = ""
    private var adapter: AdapterMessageList? = null
    private var chatService: ServiceChat? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        fabMessageList.setOnClickListener {
            createMessage()
        }
        btnAttachFile.setOnClickListener {
            chooseFileType()
        }
        setUpService()
        setUpRV()
        addFieldFileMessage()
        updateSeen()
    }

    private fun addFieldFileMessage() {

        val data = FileMessageDb().getData(user_id)
        if (data.size > 0)
            addDataToAdapter(data)

    }

    private fun chooseFileType() {
        startActivityForResult(Intent(ctx, ActivityFile::class.java), 1, null)
    }

    fun fileChoosed(path: String) {
        if (path.isEmpty())
            return
        LLFileBox.visibility = View.VISIBLE
        txtFileMessageList.text = File(path).name
        setFileBoxImage(path)
        fPath = path
    }

    private fun setFileBoxImage(path: String) {
        val f = File(path)
        when (f.extension) {
            "png", "jpeg", "jpg" -> imgFileMessageList.setImageBitmap(BitmapFactory.decodeFile(path))
            "pdf" -> imgFileMessageList.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_pdf))
        }
    }

    private fun createMessage() {
        val message = ChatMessageData(
            ChatMessageDb(user_id).getLastId() + 1,
            user_id,
            true,
            "",
            "",
            txtType.text.toString(),
            0,
            0,
            0,
            fPath,
            true
        )
        addDataToAdapter(ArrayList<ChatMessageData>().apply { add(message) })
        if (fPath.isEmpty())
            sendMessage(message)
        else
            sendFileMessage(message)
        fPath = ""
    }

    private fun sendFileMessage(cmData: ChatMessageData) {
        LLFileBox.visibility = View.GONE
        ChatPresenter(object : ChatPresenter.ChatResult {
            override fun chatResponse(ok: Boolean, message: String) {
                adapter?.let {
                    val index = it.data.indexOf(cmData)
                    it.data.get(index).apply {
                        animating = false
                        if (ok)
                            status = 1
                        it.notifyItemChanged(index)
                    }
                }
            }
        }).sendFileMessage(cmData)
    }

    private fun updateSeen() {
        ChatPresenter(object : ChatPresenter.ChatResult {}).updateSeen(user_id)
    }

    private fun setUpService() {
        val intent = Intent(ctx, ServiceChat::class.java)
        (ctx as ActivityMessageList).bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    private fun sendMessage(message: ChatMessageData) {
        txtType.setText("")
        ChatPresenter(object : ChatPresenter.ChatResult {
            override fun chatResponse(ok: Boolean, message: String) {
                toast(message)
            }
        }).sandMessage(message)
    }

    private fun setUpRV() {
        val data = ChatMessageDb(user_id).getData()
        adapter = AdapterMessageList(data, this)
        RVChatList.layoutManager = GridLayoutManager(ctx, 1)
        RVChatList.adapter = adapter
        if (adapter != null && adapter!!.data.size > 0)
            RVChatList.scrollToPosition(adapter!!.data.size - 1)
    }

    override fun newMessage(data: ArrayList<ChatMessageData>) {
        for (o in data)
            if (!o.its_my) {
                updateSeen()
                break
            }
        deleteTemplateMessage()
        deleteTemplateFileMessage()
        addDataToAdapter(data)
        playSound()
    }

    private fun deleteTemplateMessage() {
        adapter?.let {
            for (i in (it.data.size - 1) downTo (0)) {
                if (it.data[i].status == 0 && it.data[i].fPath.isEmpty()) {
                    it.data.removeAt(i)
                    it.notifyItemRemoved(i)
                } else if (it.data[i].status == 1 && it.data[i].fPath.isEmpty())
                    return
            }
        }
    }

    private fun deleteTemplateFileMessage() {
        adapter?.let {
            for (i in (it.data.size - 1) downTo (0)) {
                if (it.data[i].status == 1 && it.data[i].fPath.isNotEmpty() && it.data[i].send_date.isEmpty()) {
                    it.data.removeAt(i)
                    it.notifyItemRemoved(i)
                } else if (it.data[i].status == 1 && it.data[i].fPath.isEmpty() && it.data[i].send_date.isNotEmpty())
                    return
            }
        }
    }

    private fun addDataToAdapter(data: ArrayList<ChatMessageData>) {
        adapter?.let {
            it.data.addAll(data)
            it.notifyItemInserted(it.data.size - 1)
            it.notifyItemRangeChanged(it.data.size - 1, it.itemCount)
            RVChatList.scrollToPosition(it.data.size - 1)
        }
    }

    override fun updateSeen(lastSeenId: Int) {
        adapter?.let {
            for (i in (it.data.size - 1) downTo (0)) {
                if (it.data[i].its_my && it.data[i].status == 1 && it.data[i].m_id <= lastSeenId) {
                    it.data[i].status = 2
                    it.notifyItemChanged(i)
                } else if (it.data[i].status == 2)
                    return
            }
        }

    }

    private fun playSound() {
        MediaPlayer.create(ctx, R.raw.seen).apply {
            setVolume(0.4f, 0.4f)
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chatService?.user_id = -1
    }

    override fun myFileClicked(position: Int) {
        adapter?.let {
            val data = it.data[position]
            val f = File(data.fPath)
            if (data.status == 0 && !data.animating) {
                data.animating = true
                it.notifyItemChanged(position)
                sendFileMessage(data)
            }
            if (data.status != 0 && !data.animating) {
                if (f.exists()) {
                    openFile(f)
                } else {
                    data.animating = true
                    it.notifyItemChanged(position)
                    downloadFile(data, position)
                }
            }
        }
    }

    override fun otherFileClicked(position: Int) {
        adapter?.let {
            val data = it.data[position]
            val f = File(data.fPath)
            if (!data.animating) {
                if (f.exists()) {
                    openFile(f)
                } else {
                    data.animating = true
                    it.notifyItemChanged(position)
                    downloadFile(data, position)
                }
            }
        }
    }

    private fun downloadFile(data: ChatMessageData, position:Int) {
        FilePresenter(object : FilePresenter.FilePresenterRes {
            override fun fileResponse(ok: Boolean, path: String) {
                adapter?.let {
                    val data = it.data[position]
                    data.animating = false
                    if(ok)
                        data.fPath = path
                    it.notifyItemChanged(position)
                }
            }
        }).downloadFile(data.file_id, data.m_id, data.user_id)
    }

    private fun openFile(f: File) {
        val myMime = MimeTypeMap.getSingleton()
        val newIntent = Intent(Intent.ACTION_VIEW)
        val mimeType = myMime.getMimeTypeFromExtension(f.extension)
        newIntent.setDataAndType(Uri.fromFile(f), mimeType)
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            ctx.startActivity(newIntent)
        } catch (e: Exception) {
            toast("برنامه ایی برای نمایش این نوع فایل موجود نمی باشد.")
        }

    }

    override fun onDestroy() {
        conn.onServiceDisconnected(null)
        (ctx as ActivityMessageList).unbindService(conn)
        super.onDestroy()
    }

}
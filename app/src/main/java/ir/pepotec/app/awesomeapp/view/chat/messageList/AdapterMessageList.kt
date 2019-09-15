package ir.pepotec.app.awesomeapp.view.chat.messageList

import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.chat.ChatMessageData
import ir.pepotec.app.awesomeapp.view.chat.ActivityChat
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_receive.view.txtDateItemReceive
import kotlinx.android.synthetic.main.item_receive.view.txtReceiveItem
import kotlinx.android.synthetic.main.item_receive_file.view.*
import kotlinx.android.synthetic.main.item_receive_invite.view.*
import kotlinx.android.synthetic.main.item_send.view.imgSeen
import kotlinx.android.synthetic.main.item_send.view.txtDateItemSend
import kotlinx.android.synthetic.main.item_send.view.txtSendItem
import kotlinx.android.synthetic.main.item_send_file.view.*
import java.io.File

class AdapterMessageList(val data: ArrayList<ChatMessageData>, private val listener: AdapterMessageListEvent) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private var lastViewType = getItemViewType(0)
    val maxW: Int

    interface AdapterMessageListEvent {
        fun myFileClicked(position: Int) {}
        fun otherFileClicked(position: Int) {}
        fun acceptInvite(position: Int) {}
        fun itemClicked() {}
    }

    init {
        val p = Point()
        (App.instanse as ActivityChat).windowManager.defaultDisplay.getRealSize(p)
        maxW = p.x * 75 / 100
    }

    class SendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList) {
            itemView.apply {
                txtSendItem.text = data.m_text
                txtDateItemSend.text = "${data.send_date}  ${data.send_time}"
                txtSendItem.maxWidth = adapter.maxW
                when (data.status) {
                    1 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_done_all))
                        imgSeen.drawable.setTint(ContextCompat.getColor(App.instanse, R.color.light3))
                    }
                    2 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_done_all))
                        imgSeen.drawable.setTint(ContextCompat.getColor(App.instanse, R.color.dark))
                    }
                    0 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_progress))
                        (imgSeen.drawable as AnimatedVectorDrawable).start()
                    }
                }
            }
        }
    }

    class SendImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList, position: Int) {
            itemView.apply {
                txtSendItem.text = data.m_text
                txtDateItemSend.text = "${data.send_date}  ${data.send_time}"
                txtSendItem.maxWidth = adapter.maxW
                when (data.status) {
                    1 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_done_all))
                        imgSeen.drawable.setTint(ContextCompat.getColor(App.instanse, R.color.light3))
                    }
                    2 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_done_all))
                        imgSeen.drawable.setTint(ContextCompat.getColor(App.instanse, R.color.dark))
                    }
                }
                adapter.setImage(data.fPath, imgItemSendFile)
                setOnClickListener {
                    adapter.listener.myFileClicked(position)
                }
            }
        }
    }

    class SendFileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList, position: Int) {
            itemView.apply {
                txtSendItem.text = data.m_text
                txtDateItemSend.text = "${data.send_date}  ${data.send_time}"
                txtSendItem.maxWidth = adapter.maxW
                when (data.status) {
                    1 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_done_all))
                        imgSeen.drawable.setTint(ContextCompat.getColor(App.instanse, R.color.light3))
                    }
                    2 -> {
                        imgSeen.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_done_all))
                        imgSeen.drawable.setTint(ContextCompat.getColor(App.instanse, R.color.dark))
                    }
                }
                if (data.status == 0)
                    imgItemSendFile.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_upload))
                else {
                    if (File(data.fPath).exists())
                        imgItemSendFile.setImageDrawable(
                            ContextCompat.getDrawable(
                                App.instanse,
                                if (File(data.fPath).extension === "pdf")
                                    R.drawable.ic_pdf
                                else
                                    R.drawable.ic_file
                            )
                        )
                    else
                        imgItemSendFile.setImageDrawable(
                            ContextCompat.getDrawable(
                                App.instanse,
                                R.drawable.ic_download
                            )
                        )

                }
                if (data.animating)
                    (imgItemSendFile.drawable as AnimatedVectorDrawable).start()
                setOnClickListener {
                    adapter.listener.myFileClicked(position)
                }
            }
        }

    }

    class SendInviteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList) {
            val message = "برای پیوستن به گروه"+"<font color='red'> ${data.m_text} </font>"
            itemView.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtSendItem.setText(Html.fromHtml(message,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE)
                } else {
                    txtSendItem.setText(Html.fromHtml(message), TextView.BufferType.SPANNABLE)
                }
                txtSendItem.maxWidth = adapter.maxW
                txtDateItemSend.text = "${data.send_date}  ${data.send_time}"
            }
        }
    }

    class ReceiveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList) {
            itemView.apply {
                txtReceiveItem.text = data.m_text
                txtReceiveItem.maxWidth = adapter.maxW
                txtDateItemReceive.text = "${data.send_date}  ${data.send_time}"
            }
        }
    }

    class ReceiveImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList, position: Int) {
            itemView.apply {
                txtReceiveItem.text = data.m_text
                txtReceiveItem.maxWidth = adapter.maxW
                txtDateItemReceive.text = "${data.send_date}  ${data.send_time}"
                adapter.setImage(data.fPath, imgItemReceiveFile)
                setOnClickListener {
                    adapter.listener.otherFileClicked(position)
                }
            }
        }
    }

    class ReceiveFileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList, position: Int) {
            itemView.apply {
                txtReceiveItem.text = data.m_text
                txtReceiveItem.maxWidth = adapter.maxW
                txtDateItemReceive.text = "${data.send_date}  ${data.send_time}"

                if (File(data.fPath).exists())
                    imgItemReceiveFile.setImageDrawable(
                        ContextCompat.getDrawable(
                            App.instanse,
                            if (File(data.fPath).extension === "pdf")
                                R.drawable.ic_pdf
                            else
                                R.drawable.ic_file
                        )
                    )
                else
                    imgItemReceiveFile.setImageDrawable(
                        ContextCompat.getDrawable(
                            App.instanse,
                            R.drawable.ic_download
                        )
                    )

                if (data.animating)
                    (imgItemReceiveFile.drawable as AnimatedVectorDrawable).start()
                setOnClickListener {
                    adapter.listener.otherFileClicked(position)
                }
            }
        }
    }

    class ReceiveInviteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList, position:Int) {
            val message = "برای پیوستن به گروه"+"<font color='blue'> ${data.m_text} </font>"
            itemView.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtItemReceiveInvite.setText(Html.fromHtml(message,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE)
                } else {
                    txtItemReceiveInvite.setText(Html.fromHtml(message), TextView.BufferType.SPANNABLE)
                }
                txtItemReceiveInvite.maxWidth = adapter.maxW
                txtDateReceiveInvite.text = "${data.send_date}  ${data.send_time}"
                btnAcceptInviteReceive.setOnClickListener {
                    adapter.listener.acceptInvite(position)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = data[position]
        if (data.its_my) {
            if (data.i_id != 0)
                return 7
            if (data.fPath.isNullOrEmpty())
                return 1
            val f = File(data.fPath)
            if (((f.extension) == "png" || (f.extension) == "jpg" || (f.extension) == "jpeg") && data.status != 0 && f.exists())
                return 2
            return 3
        } else {
            if (data.i_id != 0)
                return 8
            if (data.fPath.isNullOrEmpty())
                return 4
            val f = File(data.fPath)
            if (((f.extension) == "png" || (f.extension) == "jpg" || (f.extension) == "jpeg") && (f.exists()))
                return 5
            return 6
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> SendHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send,
                    parent,
                    false
                )
            )
            2 -> SendImageHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send_file,
                    parent,
                    false
                )
            )
            3 -> SendFileHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send_file,
                    parent,
                    false
                )
            )
            4 -> ReceiveHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive,
                    parent,
                    false
                )
            )
            5 -> ReceiveImageHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive_file,
                    parent,
                    false
                )
            )
            6 -> ReceiveFileHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive_file,
                    parent,
                    false
                )
            )
            7 -> SendInviteHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send_invite,
                    parent,
                    false
                )
            )
            else -> ReceiveInviteHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive_invite,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when {
            getItemViewType(position) == 1 -> (holder as SendHolder).onBind(data[position], this@AdapterMessageList)
            getItemViewType(position) == 2 -> (holder as SendImageHolder).onBind(data[position], this@AdapterMessageList, position)
            getItemViewType(position) == 3 -> (holder as SendFileHolder).onBind(data[position], this@AdapterMessageList, position)
            getItemViewType(position) == 4 -> (holder as ReceiveHolder).onBind(data[position], this@AdapterMessageList)
            getItemViewType(position) == 5 -> (holder as ReceiveImageHolder).onBind(data[position], this@AdapterMessageList, position)
            getItemViewType(position) == 6 -> (holder as ReceiveFileHolder).onBind(data[position], this@AdapterMessageList, position)
            getItemViewType(position) == 7 -> (holder as SendInviteHolder).onBind(data[position], this@AdapterMessageList)
            else -> (holder as ReceiveInviteHolder).onBind(data[position], this@AdapterMessageList, position)
        }
    }

    override fun getItemCount(): Int = data.size

    private fun setImage(path: String, img: ImageView) {
        if (!File(path).exists())
            return
        val b = BitmapFactory.decodeFile(path)
        b?.let {
            val div = b.height.toFloat() / b.width
            val h = div * maxW
            img.apply {
                layoutParams.width = maxW
                layoutParams.height = h.toInt()
            }
        }
        AF().setImage(img, path)
    }

}
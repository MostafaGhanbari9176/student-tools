package ir.pepotec.app.awesomeapp.view.student.chat.messageList

import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.chat.ChatMessageData
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.CircularIMG
import kotlinx.android.synthetic.main.item_receive.view.*
import kotlinx.android.synthetic.main.item_receive.view.txtDateItemReceive
import kotlinx.android.synthetic.main.item_receive.view.txtReceiveItem
import kotlinx.android.synthetic.main.item_receive_file.view.*
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
        fun itemClicked() {}
    }

    init {
        val p = Point()
        (App.instanse as ActivityMessageList).windowManager.defaultDisplay.getRealSize(p)
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
                }
                adapter.setImage(data.fPath, imgItemSendFile)
            }
        }
    }

    class SendFileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList) {
            itemView.apply {
                txtReceiveItem.text = data.m_text
                txtReceiveItem.maxWidth = adapter.maxW
                txtDateItemReceive.text = "${data.send_date}  ${data.send_time}"
                adapter.setImage(data.fPath, imgItemReceiveFile)
            }
        }
    }

    class ReceiveFileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: ChatMessageData, adapter: AdapterMessageList) {
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
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = data[position]
        if (data.its_my) {
            return if (data.fPath.isNullOrEmpty())
                1
            else if (((File(data.fPath).extension) == "png" || (File(data.fPath).extension) == "jpg" || (File(data.fPath).extension) == "jpeg") && data.status != 0)
                2
            else 3
        } else {
            return if (data.fPath.isNullOrEmpty())
                4
            else if (((File(data.fPath).extension) == "png" || (File(data.fPath).extension) == "jpg" || (File(data.fPath).extension) == "jpeg"))
                5
            else 6
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1)
            SendHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send,
                    parent,
                    false
                )
            )
        else if (viewType == 2)
            SendImageHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send_file,
                    parent,
                    false
                )
            )
        else if (viewType == 3)
            SendFileHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_send_file,
                    parent,
                    false
                )
            )
        else if (viewType == 4)
            ReceiveHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive,
                    parent,
                    false
                )
            )
        else if (viewType == 5)
            ReceiveImageHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive_file,
                    parent,
                    false
                )
            )
        else
            ReceiveFileHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive_file,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 1)
            (holder as SendHolder).onBind(data[position], this@AdapterMessageList)
        else if (getItemViewType(position) == 2)
            (holder as SendImageHolder).onBind(data[position], this@AdapterMessageList)
        else if (getItemViewType(position) == 3)
            (holder as SendFileHolder).onBind(data[position], this@AdapterMessageList)
        else if (getItemViewType(position) == 4)
            (holder as ReceiveHolder).onBind(data[position], this@AdapterMessageList)
        else if (getItemViewType(position) == 5)
            (holder as ReceiveImageHolder).onBind(data[position], this@AdapterMessageList)
        else
            (holder as ReceiveFileHolder).onBind(data[position], this@AdapterMessageList)
    }

    override fun getItemCount(): Int = data.size

    private fun setImage(path: String, img: ImageView) {
        val b = BitmapFactory.decodeFile(path)
        val div = b.height.toFloat() / b.width
        val h = div * maxW
        img.apply {
            layoutParams.width = maxW
            layoutParams.height = h.toInt()
            setImageBitmap(b)
        }
    }

}
package ir.pepotec.app.awesomeapp.view.student.chat.chatList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.chat.ChatListData
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.CircularIMG
import kotlinx.android.synthetic.main.item_chat_contact.view.*

class AdapterChatList(private val data: ArrayList<ChatListData>, private val listener: (userId: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_chat_contact, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            with(data[position])
            {
                txtSIdChatContact.text = s_id
                txtLastMessageChatContact.text = message
                setImage(user_id, imgChatContact)
            }
            setOnClickListener {
                listener(data[position].user_id)
            }
        }
    }

    private fun setImage(userId: Int, img: ImageView) {
        StudentProfilePresenter(object:StudentProfilePresenter.StudentProfileResult{
            override fun studentImgData(data: ByteArray?) {
                AF().setImage(img, data, true)
            }
        }).downOtherImg(userId)
    }

}
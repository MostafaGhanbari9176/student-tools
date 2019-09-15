package ir.pepotec.app.awesomeapp.view.chat.chatList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.chat.ChatListData
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChat
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.chat.ActivityChat
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_chat_list.view.*

class AdapterChatList(private val data: ArrayList<ChatListData>, private val listener: (position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyHolder(
            LayoutInflater.from(App.instanse).inflate(
                R.layout.item_chat_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {

            setMargin(this, if (position == 0) R.dimen.margin_16 else R.dimen.margin_4)

            with(data[position])
            {
                txtSIdChatContact.text = chat_subject
                txtLastMessageChatContact.text = message
                AF().setImage(
                    imgChatContact, if (kind_id == "s") {
                        StudentProfile.baseUrl
                    } else {
                        GroupChat.baseUrl
                    } + "downImg", chat_id, new = false,
                    cache = true
                )
            }
            setOnClickListener {
                listener(position)
            }
        }
    }

    private fun setMargin(v: View, @DimenRes marginId: Int) {
        (v.layoutParams as RecyclerView.LayoutParams).topMargin =
            (App.instanse as ActivityChat).resources.getDimensionPixelSize(marginId)
    }

}
package ir.pepotec.app.awesomeapp.view.student.chat.chatList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_receive.view.*
import kotlinx.android.synthetic.main.item_send.view.*

class AdapterChatList : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private var lastViewType = getItemViewType(0)

    class SendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(
            position: Int,
            adapter: AdapterChatList
        ) {
            if (position == 0)
                adapter.setMargin(itemView, -1)
            else if (adapter.getItemViewType(position-1) != 1) {
                adapter.setMargin(itemView, -1)
                itemView.LLDateItemSend.visibility = View.VISIBLE

            } else{
                itemView.LLDateItemSend.visibility = View.GONE
                adapter.setMargin(itemView, 0)
            }
        }
    }

    class ReceiveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(
            position: Int,
            adapter: AdapterChatList
        ) {
            if (position == 0)
                adapter.setMargin(itemView, -1)
            else if (adapter.getItemViewType(position -1 ) != 2) {
                adapter.setMargin(itemView, -1)
                itemView.LLDateItemReceive.visibility = View.VISIBLE
            } else {
                adapter.setMargin(itemView, 0)
                itemView.LLDateItemReceive.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position / 2) % 2 == 0) 1/*send*/ else 2/*receive*/
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
        else
            ReceiveHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_receive,
                    parent,
                    false
                )
            )
    }

    override fun getItemCount(): Int = 32

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 1)
            (holder as SendHolder).onBind(position, this@AdapterChatList)
        else
            (holder as ReceiveHolder).onBind(position, this@AdapterChatList)
    }

    private fun setMargin(v: View, i: Int) {
        (v.layoutParams as GridLayoutManager.LayoutParams).topMargin =
            if (i == -1)
                (App.instanse as ActivityStudent).resources.getDimensionPixelSize(R.dimen.margin_32)
            else
                (App.instanse as ActivityStudent).resources.getDimensionPixelSize(R.dimen.margin_8)

    }
}
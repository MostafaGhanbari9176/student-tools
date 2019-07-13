package ir.pepotec.app.awesomeapp.view.main.home

import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_home.view.*

class AdapterHomeList(val listener: (position: Int) -> Unit) : RecyclerView.Adapter<AdapterHomeList.Holder>() {

    private val source = ArrayList<String>()

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(App.instanse).inflate(R.layout.item_home, parent, false))
    }

    override fun getItemCount(): Int = 16 + 2

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.apply {
            setItemSize(this)
            alpha = if (position >= itemCount - 2) 0f else 1f

            setOnClickListener {
                if (position < itemCount - 2)
                    listener(position)
            }

        }
    }

    private fun setItemSize(itemView: View) {
        val p = Point()
        ((App.instanse) as ActivityMain).windowManager.defaultDisplay.getRealSize(p)
        val size: Int = p.x / 2
        itemView.imgIHome.layoutParams.width = size
        itemView.imgIHome.layoutParams.height = size
    }
}
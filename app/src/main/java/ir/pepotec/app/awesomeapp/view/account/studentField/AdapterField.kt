package ir.pepotec.app.awesomeapp.view.account.studentField

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.student.FieldPresenter
import ir.pepotec.app.awesomeapp.view.uses.App

class AdapterField(
    private val data: ArrayList<FieldPresenter.FieldData>,
    private val listener: (fieldId: Int, fieldName: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var position = -1
    private var count = 0

    class ParentHolder(v: View) : RecyclerView.ViewHolder(v)
    class ChildHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun getItemViewType(position: Int): Int {
        return if (data[position].parentId == -1) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val parentView = MaterialButton(App.instanse).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setTextColor(Color.WHITE)
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }

        val childView = MaterialButton(App.instanse).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.GRAY)
        }

        return if (viewType == 1) ParentHolder(parentView)
        else ChildHolder(childView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as MaterialButton).apply {
            setOnClickListener {
                if (data[position].parentId == -1)
                    addToList(position)
                else
                    listener(data[position].fieldId, data[position].name)
            }
            text = data[position].name
        }
    }

    private fun addToList(position: Int) {
        if (this.position != -1)
            removeOldChild()
        if (position > this.position)
            this.position = position - count
        else
            this.position = position
        this.count = data[this.position].child.size
        data.addAll(this.position + 1, data[this.position].child)
        notifyItemRangeInserted(this.position, count)
        notifyItemRangeChanged(this.position, data.size)
    }

    private fun removeOldChild() {
        for (i in (position + count) downTo (position + 1)) {
            data.removeAt(i)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(position, count)
        notifyItemRangeChanged(position, data.size)
    }
}
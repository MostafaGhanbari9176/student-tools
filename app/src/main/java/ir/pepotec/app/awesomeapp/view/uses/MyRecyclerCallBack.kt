package ir.pepotec.app.awesomeapp.view.uses

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.AdapterFriendList

class MyRecyclerCallBack(
    private val listener: RecyclerCallBack, @DrawableRes leftIcon: Int?, @DrawableRes rightIcon: Int?, @ColorRes leftColor: Int?, @ColorRes rightColor: Int?,
    private val rightLimit: Boolean,
    private val leftLimit: Boolean
) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if(viewHolder.itemView.getTag() == "bottom")
            return makeMovementFlags(0,0)
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    interface RecyclerCallBack {
        fun leftSwipe(position:Int){}
        fun rightSwipe(position:Int){}
    }

    val ctx = App.instanse
    val leftIconD = leftIcon?.let { ContextCompat.getDrawable(ctx, it) }
    val rightIconD = rightIcon?.let { ContextCompat.getDrawable(ctx, it) }
    val leftColorD = leftColor?.let { ColorDrawable(ContextCompat.getColor(ctx, it)) }
    val rightColorD = rightColor?.let { ColorDrawable(ContextCompat.getColor(ctx, it)) }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> listener.leftSwipe(viewHolder.adapterPosition)
            ItemTouchHelper.RIGHT -> listener.rightSwipe(viewHolder.adapterPosition)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            if (dX > 0 && rightLimit) dX / 2 else if (dX < 0 && leftLimit) dX / 2 else dX,
            dY,
            actionState,
            isCurrentlyActive
        )

        val itemView = viewHolder.itemView
        val margin = (itemView.height - (leftIconD?.intrinsicHeight ?: rightIconD!!.intrinsicHeight)) / 4
        val top = itemView.top + (itemView.height - (leftIconD?.intrinsicHeight ?: rightIconD!!.intrinsicHeight)) / 2
        val bottom = top + (leftIconD?.intrinsicHeight ?: rightIconD!!.intrinsicHeight)

        if (dX > 0) {//right
            val left = itemView.left + margin
            val right = left + (leftIconD?.intrinsicWidth ?: rightIconD!!.intrinsicWidth)
            rightIconD?.setBounds(left, top, right, bottom)
            rightColorD?.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
            rightColorD?.draw(c)
            rightIconD?.draw(c)
        } else if (dX < 0) {//left
            val right = itemView.right - margin
            val left = right - (leftIconD?.intrinsicWidth ?: rightIconD!!.intrinsicWidth)
            leftIconD?.setBounds(left, top, right, bottom)
            leftColorD?.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
            leftColorD?.draw(c)
            leftIconD?.draw(c)
        } else {

        }

    }

}
package ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_student_list.view.*

class AdapterFriendList(
    var data: ArrayList<StudentProfileData>,
    private val listener: (userId: Int) -> Unit,
    private val reachesBottom: () -> Unit
) : RecyclerView.Adapter<AdapterFriendList.MyHolder>() {

    class MyHolder(IV: View) : RecyclerView.ViewHolder(IV)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(App.instanse).inflate(R.layout.item_student_list, parent, false)
        return MyHolder(v).apply { itemView.tag = if (viewType == 1) "normal" else "bottom" }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != itemCount - 1 && !data[position].owner) 1 else 2
    }

    override fun getItemCount(): Int = data.size + 1

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            alpha = if (position == itemCount - 1) 0f else 1f
            if (position != itemCount - 1) {
                imgSelfItemStudent.visibility = if (data[position].account) View.VISIBLE else View.GONE
                txtAdminItemStudent.visibility = if (data[position].owner) View.VISIBLE else View.GONE
                txtNameItemStudent.text = data.get(position).user_name
                txtSIdItemStudent.text = data.get(position).s_id
                AF().setImage(imgItemStudent, StudentProfile.baseUrl+"downImg", data[position].user_id, false, true)
                setOnClickListener {
                    listener(data.get(position).user_id)
                }
            }
        }
        if (position == itemCount - 1)
            reachesBottom()
    }


}
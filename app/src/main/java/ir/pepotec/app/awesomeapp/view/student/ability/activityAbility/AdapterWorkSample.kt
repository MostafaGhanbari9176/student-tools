package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.workSample.workSampleList
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_add_work_sample.view.*
import kotlinx.android.synthetic.main.item_work_sample.view.*

class AdapterWorkSample(
    private val data: ArrayList<workSampleList>,
    private val itsMy: Boolean,
    private val listener: (workSampleId: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

    class AddHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(gone: Boolean) {
            itemView.CVItemAddWorkSample.visibility = if (gone) View.GONE else View.VISIBLE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 2 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) MyHolder(
            LayoutInflater.from(App.instanse).inflate(
                R.layout.item_work_sample,
                parent,
                false
            )
        )
        else AddHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_add_work_sample, parent, false))
    }

    override fun getItemCount(): Int = data.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener(if (position == 0) -1 else data.get(position - 1).work_sample_id)
        }
        if (position == 0) {
            (holder as AddHolder).onBind(data.size >= 2 || !itsMy)
            return
        }
        (holder as MyHolder).itemView.apply {
            txtEyeNumberItemWorkSample.text = data.get(position - 1).seen_num.toString()
            txtLikeNumberItemWorkSample.text = data.get(position - 1).like_num.toString()
            downImage("${data.get(position - 1).work_sample_id}0", imgItemWorkSample)
        }

    }

    private fun downImage(id: String, v: ImageView) {
        WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
            override fun workSampleImgData(data: ByteArray?) {
               // AF().setImage(v, data)
            }
        }).workSampleImg(id)
    }


}
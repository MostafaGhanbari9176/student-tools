package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.workSample.workSampleList
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_work_sample.view.*

class AdapterWorkSample(private val data: ArrayList<workSampleList>, private val listener:(workSampleId:String) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

    class AddHolder(v:View):RecyclerView.ViewHolder(v)

    override fun getItemViewType(position: Int): Int {
        return if(position!=0) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1) MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_work_sample ,parent, false))
        else AddHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_add_work_sample, parent, false))
    }

    override fun getItemCount():Int = data.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     holder.itemView.apply {
         setOnClickListener {
             listener(if(position == 0) "" else data.get(position -1 ).id)
         }
         if(position > 0)
         {
             txtEyeNumberItemWorkSample.text = data.get(position - 1).eyeNumber.toString()
             txtLikeNumberItemWorkSample.text = data.get(position - 1).likeNumber.toString()
             downImage(data.get(position -1 ).imgId, imgItemWorkSample)
         }
     }
    }

    private fun downImage(id:String, v:ImageView)
    {
        setImage(null, v)
        WorkSamplePresenter(object :WorkSamplePresenter.WorkSampleResult{
            override fun workSampleImgData(data: ByteArray?) {
                setImage(data, v)
            }
        }).workSampleImg(id)
    }

    private fun setImage(data:ByteArray?, v:ImageView) {
        Glide.with(App.instanse)
            .load(data)
            .error(R.drawable.ic_broken_img)
            .into(v)
    }
}
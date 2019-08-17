package ir.pepotec.app.awesomeapp.view.file

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_file.view.*
import java.io.File

class AdapterFile(var data: Array<File>, private val listener: (path: String) -> Unit) :
    RecyclerView.Adapter<AdapterFile.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_file, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val itemData = data[position]
        holder.itemView.apply {
            txtItemFile.text = itemData.name
            if(itemData.isDirectory)
                imgItemFile.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_folder))
            else
                when(itemData.extension)
                {
                    "png","jpeg","jpg"-> imgItemFile.setImageBitmap(BitmapFactory.decodeFile(itemData.absolutePath))
                    "pdf"-> imgItemFile.setImageDrawable(ContextCompat.getDrawable(App.instanse, R.drawable.ic_pdf))
                }
            setOnClickListener {
                listener(itemData.absolutePath)
            }
        }
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

}
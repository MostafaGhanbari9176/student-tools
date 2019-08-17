package ir.pepotec.app.awesomeapp.view.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_select_file_type.*
import kotlinx.android.synthetic.main.item_file.view.*

class FragmentSelectType:MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_file_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()
    {
        itemDocument.apply {
            txtItemFile.text = "PDF"
            imgItemFile.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_pdf))
            setOnClickListener {
                (ctx as MyActivity).changeView(FragmentFileList().apply { type = "pdf" })
            }
        }
        itemImage.apply {
            txtItemFile.text = "Image"
            imgItemFile.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_image))
            setOnClickListener {
                (ctx as MyActivity).changeView(FragmentFileList().apply { type = "img" })
            }
        }
    }
}
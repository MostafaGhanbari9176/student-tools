package ir.pepotec.app.awesomeapp.view.file

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_file_list.*
import java.io.File

class FragmentFileList:MyFragment() {

    var type = ""
    var rootPath = android.os.Environment.getExternalStorageDirectory().absolutePath
    var adapter:AdapterFile? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()
    {
        btnBackFile.setOnClickListener {
            val f = File(rootPath)
            val pFile = f.parentFile
            if(pFile.exists() && pFile.canRead())
                changeAdapterData(pFile.listFiles())
            else
                (ctx as ActivityFile).finish()
        }
        setUpRV()
    }

    private fun setUpRV()
    {
        val data = File(rootPath).listFiles()
        adapter = AdapterFile(data){
            itemClicked(it)
        }
        RVFileList.layoutManager = GridLayoutManager(ctx, 2)
        RVFileList.adapter = adapter
    }

    private fun itemClicked(path: String)
    {
        val f = File(path)
        if(f.isDirectory )
        {
            rootPath = path
            changeAdapterData(f.listFiles())
        }else
            fileChoosed(path)
    }

    private fun changeAdapterData(listFiles: Array<File>)
    {
        adapter?.let {
            it.data = listFiles
            it.notifyDataSetChanged()
        }
    }

    private fun fileChoosed(path:String)
    {
        (ctx as ActivityFile).apply {
            val i = intent
            i.putExtra("path", path)
            setResult(Activity.RESULT_OK, i)
            finish()
        }
    }

}
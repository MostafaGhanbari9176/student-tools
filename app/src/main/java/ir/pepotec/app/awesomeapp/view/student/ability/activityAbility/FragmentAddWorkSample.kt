package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_work_sample.*

class FragmentAddWorkSample : MyFragment() {

    var subject = ""
    var description = ""
    var add = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_work_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (subject.length > 0) {
            add = false
            setData()
        }
        btnSelectCoverImg.setOnClickListener {
            selectImg(true)
        }
        btnSelectWorkSampleImg.setOnClickListener {
            selectImg(false)
        }
    }

    private fun selectImg(cover: Boolean) {
        val pick = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (!cover)
            pick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(pick, if (cover) 1 else 2)

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    private fun setData() {
        txtSubjectAddWorkSample.setText(subject)
        txtdescriptAddWorkSample.setText(description)
        txtTtlAddWorkSample.text = "ویرایش $subject"
    }
}
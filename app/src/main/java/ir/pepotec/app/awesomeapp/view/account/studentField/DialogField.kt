package ir.pepotec.app.awesomeapp.view.account.studentField

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.student.FieldPresenter
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.dialog_field.view.*

class DialogField(private val listener:(fieldId:Int, fieldName:String) -> Unit) : Dialog(App.instanse) {

    private val v = LayoutInflater.from(App.instanse).inflate(R.layout.dialog_field, null, false)

    init {
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getData()
        show()
    }

    private fun getData() {
        FieldPresenter(object : FieldPresenter.Res {
            override fun result(ok: Boolean, message: String, data: ArrayList<FieldPresenter.FieldData>?) {
                v.progressField.visibility = View.GONE
                if (ok) {
                    v.RVField.apply {
                        adapter = AdapterField(data!!){fieldId, fieldName -> listener(fieldId, fieldName); cancel() }
                        layoutManager = LinearLayoutManager(App.instanse)
                    }
                } else
                    cancel()
            }
        }).get()
    }

}
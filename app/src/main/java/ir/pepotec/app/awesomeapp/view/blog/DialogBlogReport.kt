package ir.pepotec.app.awesomeapp.view.blog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.BlogPresenter
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.dialog_blog_report.view.*
import org.jetbrains.anko.toast

class DialogBlogReport(private val m_id: Int) : Dialog(App.instanse) {

    val v: View = LayoutInflater.from(context).inflate(R.layout.dialog_blog_report, null, false)

    init {
        init()
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
    }

    private fun init() {
        v.btnBlogReport1.setOnClickListener { sendToServer(1) }
        v.btnBlogReport2.setOnClickListener { sendToServer(2) }
        v.btnBlogReport3.setOnClickListener { sendToServer(3) }
        v.btnBlogReport4.setOnClickListener { sendToServer(4) }
        v.btnBlogReport5.setOnClickListener { sendToServer(5) }
    }

    private fun sendToServer(status: Int) {
        context.toast("از گزارش شما متشکرم.")
        BlogPresenter(object : BlogPresenter.BlogResult {}).changeStatus(m_id, status)
        cancel()
    }
}
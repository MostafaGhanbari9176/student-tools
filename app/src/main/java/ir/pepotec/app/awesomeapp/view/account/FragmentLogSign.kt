package ir.pepotec.app.awesomeapp.view.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_log_sign.*

class FragmentLogSign :MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btnChoseSignUp.setOnClickListener{
            changeView(FragmentTerms())
        }
        btnChoseLogIn.setOnClickListener {
            changeView(FragmentLogIn())
        }
        btnChoseLogIn.setOnLongClickListener {
            startActivity(Intent(ctx, ActivityMain()::class.java))
            true
        }
    }

    private fun changeView(f: MyFragment) {
        (ctx as ActivityAccount).changeView(f)
    }
}
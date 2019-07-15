package ir.pepotec.app.awesomeapp.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_terms.*

class FragmentTerms:MyFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_terms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()
    {
        cbxTerms.isChecked = false
        btnNextTerms.isEnabled = cbxTerms.isChecked
        cbxTerms.setOnClickListener {
            btnNextTerms.isEnabled = cbxTerms.isChecked
        }
        btnNextTerms.setOnClickListener {
            (ctx as ActivityAccount).changeView(FragmentGetPhone())
        }
    }

}
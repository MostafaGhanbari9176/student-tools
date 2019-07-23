package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_show_ability.*

class FragmentShowAbility : MyFragment() {

    private lateinit var popMenu:PopupMenu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpPopMenu()
        fabShowAbility.setOnClickListener {
            popMenu.show()
        }
        setUpRV()
        txtDateShowAbility.requestFocus()
    }

    private fun setUpPopMenu() {
        popMenu = PopupMenu(ctx, fabShowAbility)
        popMenu.apply {
            menuInflater.inflate(R.menu.menu_ability, menu)
            setOnMenuItemClickListener {
                when(it.itemId)
                {
                    R.id.menuAbilityEdit -> {
                        val f = FragmentAddAbility()
                        f.apply {
                            subject = "برنامه نویسی اندروید"
                            description = ctx.getString(R.string.test)
                            resume = "دوسال کار در شرکت واژن"
                        }
                            (ctx as ActivityAbility).changeView(f)
                        }

                    R.id.menuAbilityHide -> {
                        showHideDialog()
                    }
                    R.id.menuAbilityDelete -> {
                        showDeleteDialog()
                    }
                }
                true
            }
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(ctx)
            .setTitle("حذف مهارت")
            .setMessage("مهارت شما همراه با نمونه کارهای آن حذف می شود")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which -> toast("ok") })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which -> toast("cancel") })
            .show()
    }

    private fun showHideDialog() {
        MaterialAlertDialogBuilder(ctx)
            .setTitle("عدم نمایش مهارت")
            .setMessage("با این کار کاربران مهارت شما را نمی بینند")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which -> toast("ok") })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which -> toast("cancel") })
            .show()
    }

    private fun showOrAddWorkSample(add: Boolean) {
        (ctx as ActivityAbility).changeView(if (add) FragmentAddWorkSample() else FragmentShowWorkSample())
    }

    private fun setUpRV() {
        RVWorkSample.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        RVWorkSample.adapter = AdapterWorkSample() {
            showOrAddWorkSample(it == 0)
        }
    }
}
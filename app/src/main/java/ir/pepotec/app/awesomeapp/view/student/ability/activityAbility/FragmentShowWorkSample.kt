package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_show_work_sample.*

class FragmentShowWorkSample:MyFragment() {

    private lateinit var popMenu: PopupMenu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_work_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpPopMenu()
        fabShowWorkSample.setOnClickListener {
            popMenu.show()
        }
    }

    private fun setUpPopMenu() {
        popMenu = PopupMenu(ctx, fabShowWorkSample)
        popMenu.apply {
            menuInflater.inflate(R.menu.menu_ability, menu)
            setOnMenuItemClickListener {
                when(it.itemId)
                {
                    R.id.menuAbilityEdit -> {
                        val f = FragmentAddWorkSample()
                        f.apply {
                            subject = "چالش مستر مغز"
                            description = ctx.getString(R.string.test)
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
            .setTitle("حذف نمونه کار؟")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which -> toast("ok") })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which -> toast("cancel") })
            .show()
    }

    private fun showHideDialog() {
        MaterialAlertDialogBuilder(ctx)
            .setTitle("عدم نمایش نمونه کار")
            .setMessage("با این کار کاربران نمونه کار شما را نمی بینند")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which -> toast("ok") })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which -> toast("cancel") })
            .show()
    }

}
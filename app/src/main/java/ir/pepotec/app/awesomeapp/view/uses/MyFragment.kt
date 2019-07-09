package ir.pepotec.app.awesomeapp.view.uses

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class MyFragment : Fragment() {
    val ctx = App.instanse
    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(ctx, message, length).show()
    }
}
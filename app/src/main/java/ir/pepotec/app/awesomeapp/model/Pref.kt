package ir.pepotec.app.awesomeapp.model

import android.content.Context
import ir.pepotec.app.awesomeapp.view.uses.App

class Pref {

    companion object
    {
        val phone = "phone"
    }

    private val p = App.instanse.getSharedPreferences("MostafaGhanbari", Context.MODE_PRIVATE)

    fun getBollValue(name: String, defValue: Boolean): Boolean {
        return p.getBoolean(name, defValue)
    }

    fun saveBollValue(name: String, value: Boolean): Boolean {
        val editor = p.edit()
        editor.putBoolean(name, value)
        return editor.commit()
    }

    fun getIntegerValue(name: String, defValue: Int): Int {
        return p.getInt(name, defValue)
    }

    fun saveIntegerValue(name: String, value: Int): Boolean {
        val editor = p.edit()
        editor.putInt(name, value)
        return editor.commit()
    }

    fun getStringValue(name: String, defValue: String): String {
        return p.getString(name, defValue)
    }

    fun saveStringValue(name: String, value: String): Boolean {
        val editor = p.edit()
        editor.putString(name, value)
        return editor.commit()
    }

    fun removeValue(name: String): Boolean {
        val editor = p.edit()
        editor.remove(name)
        return editor.commit()
    }
}
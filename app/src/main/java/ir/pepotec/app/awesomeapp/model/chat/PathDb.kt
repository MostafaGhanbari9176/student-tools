package ir.pepotec.app.awesomeapp.model.chat

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.pepotec.app.awesomeapp.view.uses.App

class PathDb : SQLiteOpenHelper(App.instanse, "path_db", null, 1) {

    private val tbName = "path_table"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tbName (p_id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tbName")
    }

    fun addPath(path: String) {
        this.writableDatabase.apply {
            insert(tbName, null, ContentValues().apply {
                put("path", path)
            })
            close()
        }
    }

    fun getLastId(): Int {
        var data = -1
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT p_id FROM $tbName ORDER BY p_id DESC LIMIT 1", null)
        if (cursor.moveToFirst())
            data = cursor.getInt(cursor.getColumnIndex("p_id"))
        cursor.close()
        reader.close()
        return data
    }

    fun getPath(id: Int): String {
        var data = ""
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT path FROM $tbName WHERE p_id = $id", null)
        if (cursor.moveToFirst())
            data = cursor.getString(cursor.getColumnIndex("path"))
        cursor.close()
        reader.close()
        return data
    }

}
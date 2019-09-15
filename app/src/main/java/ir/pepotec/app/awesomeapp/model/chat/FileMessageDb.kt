package ir.pepotec.app.awesomeapp.model.chat

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.pepotec.app.awesomeapp.view.uses.App

class FileMessageDb : SQLiteOpenHelper(App.instanse, "message_file_db", null, 1) {

    val tbName = "message_file_table"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tbName (m_id INTEGER PRIMARY KEY , m_text TEXT, f_path TEXT, chat_id INTEGER, kind_id TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tbName")
    }

    fun saveData(data: ChatMessageData, chat_id:Int, kind_id:Int) {
        this.writableDatabase.apply {

            insert(tbName, null,
                ContentValues().apply
                {
                    with(data)
                    {
                        put("m_text", m_text)
                        put("m_id", m_id)
                        put("f_path", fPath)
                        put("chat_id", chat_id)
                        put("kind_id", kind_id)
                    }
                })

            close()
        }
    }

    fun getData(chat_id:Int, kind_id:String): ArrayList<ChatMessageData> {
        val data = ArrayList<ChatMessageData>()
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT * FROM $tbName WHERE chat_id = $chat_id AND kind_id = '$kind_id' ORDER BY m_id ASC", null)
        if (cursor.moveToFirst()) {
            do {
                data.add(
                    ChatMessageData(
                        cursor.getInt(0),
                        cursor.getInt(3),
                        true,
                        "",
                        "",
                        cursor.getString(1),
                        0,
                        0,
                        0,
                        cursor.getString(2),
                        0,
                        false
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        reader.close()
        return data
    }

    fun deleteData(mId:Int) {
        (this.writableDatabase as SQLiteDatabase).apply {
            execSQL("DELETE FROM $tbName WHERE m_id = $mId")
            close()
        }
    }

}
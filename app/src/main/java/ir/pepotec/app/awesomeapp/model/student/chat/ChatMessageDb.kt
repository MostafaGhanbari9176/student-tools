package ir.pepotec.app.awesomeapp.model.student.chat

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.pepotec.app.awesomeapp.view.uses.App

class ChatMessageDb(private val userId: Int) : SQLiteOpenHelper(App.instanse, "chat_message$userId", null, 1) {

    private val tbName = "m$userId"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tbName (m_id INTEGER PRIMARY KEY, m_text TEXT, its_my BOOLEAN, send_date TEXT, send_time TEXT, status INTEGER, file_id INTEGER, f_path TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tbName")
    }

    fun saveData(data: ArrayList<ChatMessageData>) {
        this.writableDatabase.apply {
            for (o in data) {
                insert(tbName, null,
                    ContentValues().apply
                    {
                        with(o)
                        {
                            put("m_id", m_id)
                            put("m_text", m_text)
                            put("send_date", send_date)
                            put("send_time", send_time)
                            put("its_my", its_my)
                            put("file_id", file_id)
                            put("f_path", fPath ?: "")
                            put("status", status)
                        }
                    })
            }
            close()
        }
    }

    fun getData(): ArrayList<ChatMessageData> {
        val data = ArrayList<ChatMessageData>()
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT * FROM $tbName ORDER BY m_id ASC", null)
        if (cursor.moveToFirst()) {
            do {
                data.add(
                    ChatMessageData(
                        cursor.getInt(0),
                        userId,
                        cursor.getInt(2) == 1,
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(1),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        0,
                        cursor.getString(7),
                        false
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        reader.close()
        return data
    }

    fun getUnsuccessful():ArrayList<String>{
        val data = ArrayList<String>()
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT m_text FROM $tbName WHERE status = 0", null)
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(cursor.getColumnIndex("m_text")))
            } while (cursor.moveToNext())
        }
        cursor.close()
        reader.close()
        return data
    }

    fun deleteUnsuccessful() {
        (this.writableDatabase as SQLiteDatabase).apply {
            execSQL("DELETE FROM $tbName WHERE status = 0")
            close()
        }
    }

    fun getLastId(): Int {
        var id = -1
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT m_id FROM $tbName ORDER BY m_id DESC LIMIT 1", null)
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor.close()
        reader.close()
        return id
    }

    fun removeTable() {
        this.writableDatabase.apply {
            execSQL("DELETE FROM $tbName")
            close()
        }
    }

    fun newMessage(data: ChatMessageData) {
        this.writableDatabase.apply {

            insert(tbName, null,
                ContentValues().apply
                {
                    with(data)
                    {
                        put("m_id", m_id)
                        put("m_text", m_text)
                        put("send_date", send_date)
                        put("send_time", send_time)
                        put("its_my", its_my)
                        put("file_id", file_id)
                        put("f_path", fPath ?: "")
                        put("status", status)
                    }
                })

            close()
        }
    }

    fun updateSeen(lastSeenId:Int){
        this.writableDatabase.apply {
            execSQL("UPDATE $tbName SET status = 2 WHERE m_id <= $lastSeenId")
            close()
        }
    }

    fun deleteDb() {
        this.writableDatabase.apply {
            App.instanse.deleteDatabase(path)
            close()
        }
    }

    fun updatePath(mId: Int, path:String) {
        this.writableDatabase.apply {
            execSQL("UPDATE $tbName SET fPath = $path WHERE m_id = $mId")
            close()
        }
    }

}
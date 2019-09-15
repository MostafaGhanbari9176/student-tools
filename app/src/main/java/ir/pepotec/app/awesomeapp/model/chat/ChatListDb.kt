package ir.pepotec.app.awesomeapp.model.chat

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.pepotec.app.awesomeapp.view.uses.App

class ChatListDb : SQLiteOpenHelper(App.instanse, "chat_list", null, 1) {

    val tbName = "chat_list_tb"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tbName (c_id INTEGER PRIMARY KEY AUTOINCREMENT, chat_id INTEGER , chat_subject TEXT, last_message TEXT DEFAULT ' ', kind_id TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tbName")
    }

    fun lastId(): Int {
        var id = -1
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT c_id FROM $tbName ORDER BY c_id DESC LIMIT 1", null)
        if (cursor.moveToFirst())
            id = cursor.getInt(0)
        cursor.close()
        reader.close()
        return id
    }

    fun firstId(): Int {
        var id = -1
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT c_id FROM $tbName ORDER BY c_id ASC LIMIT 1", null)
        if (cursor.moveToFirst())
            id = cursor.getInt(0)
        cursor.close()
        reader.close()
        return id
    }

/*    fun saveData(data: ChatListData) {
        this.writableDatabase.apply {
            insert(tbName, null,
                ContentValues().apply {
                    with(data)
                    {
                        put("chat_id", chat_id)
                        put("chat_subject", chat_subject)
                        put("last_message", message ?: "")
                    }
                })
            close()
        }
    }*/

    fun saveData(data: ArrayList<ChatListData>) {
        this.writableDatabase.apply {
            for (o in data) {
                insert(tbName, null,
                    ContentValues().apply {
                        with(o)
                        {
                            put("chat_id", chat_id)
                            put("kind_id", kind_id)
                            put("chat_subject", chat_subject)
                            put("last_message", message ?: "")
                        }
                    })
            }
            close()
        }
    }

    fun getData(kind_id:String): ArrayList<ChatListData> {
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT * FROM $tbName WHERE kind_id = '$kind_id'", null)
        val data = ArrayList<ChatListData>()
        if (cursor.moveToFirst()) {
            do {
                data.add(
                    ChatListData(
                        cursor.getInt(1),
                        cursor.getString(4),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        reader.close()
        return data
    }

    fun getAllData(): ArrayList<ChatListData> {
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT * FROM $tbName", null)
        val data = ArrayList<ChatListData>()
        if (cursor.moveToFirst()) {
            do {
                data.add(
                    ChatListData(
                        cursor.getInt(1),
                        cursor.getString(4),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        reader.close()
        return data
    }

    fun getSingle(id: Int): ChatListData? {
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT * FROM $tbName WHERE c_id = $id", null)
        var data: ChatListData? = null
        if (cursor.moveToFirst()) {
            data = ChatListData(
                cursor.getInt(1),
                cursor.getString(4),
                cursor.getString(2),
                cursor.getString(3)
            )
        }
        cursor.close()
        reader.close()
        return data
    }

    fun getSubject(chat_id:Int, kind_id:String):String{
        var sub = ""
        val reader = this.readableDatabase
        val cursor = reader.rawQuery("SELECT chat_subject FROM $tbName WHERE chat_id = $chat_id AND kind_id = '$kind_id'",null)
        if(cursor.moveToFirst())
        {
            sub = cursor.getString(0)
        }
        return sub
    }

    fun itsExists(userId: Int): Boolean {
        var count = 0
        val reader = this.readableDatabase
        val cursor: Cursor = reader.rawQuery("SELECT COUNT(*) FROM $tbName", null)
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        reader.close()
        return count > 0
    }

    fun updateMessage(userId: Int, message: String) {
        this.writableDatabase.apply {
            execSQL("UPDATE $tbName SET last_message = $message WHERE chat_id = $userId")
            close()
        }
    }

    fun deleteData() {
        this.writableDatabase.apply {
            execSQL("DELETE FROM $tbName WHERE 1")
            close()
        }
    }

    fun deleteDb() {
        this.writableDatabase.apply {
            App.instanse.deleteDatabase(path)
            close()
        }
    }

}
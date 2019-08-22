package ir.pepotec.app.awesomeapp.presenter

import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.file.FileModel
import ir.pepotec.app.awesomeapp.model.student.chat.ChatMessageDb
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.App
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class FilePresenter(private val listener:FilePresenterRes) {

    interface FilePresenterRes{
        fun fileResponse(ok:Boolean, path:String){}
    }

    fun downloadFile(fileId:Int, mId:Int, userId:Int)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        FileModel(object:FileModel.FileRes{
            override fun response(res: ServerRes?) {
                var data = ""
                res?.let {
                    data = it.data[0]
                }
                if(data.isEmpty())
                    listener.fileResponse(false, "")
                else
                    downloadFile(fileId, data, mId, userId)
            }
        }).getType(phone, ac, fileId)
    }

    private fun downloadFile(fileId:Int, fileType:String, mId:Int, userId:Int)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        FileModel(object:FileModel.FileRes{
            override fun fileData(res: ByteArray?) {
                res?.let {
                    val p = App.rootDir+"/$fileId.$fileType"
                    val f = File(p)
                    ChatMessageDb(userId).updatePath(mId, p)
                    f.createNewFile()
                    val bos = BufferedOutputStream(FileOutputStream(f))
                    bos.write(it)
                    bos.flush()
                    bos.close()
                    listener.fileResponse(true, p)
                }?:run{
                    listener.fileResponse(false, "")
                }
            }
        }).downloadFile(phone, ac, fileId)
    }

}
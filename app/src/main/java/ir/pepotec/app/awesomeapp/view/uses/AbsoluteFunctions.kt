package ir.pepotec.app.awesomeapp.view.uses

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class AbsoluteFunctions {

    fun convertBitMapToFile(b: Bitmap, ctx:Context): File {
        //create a file to write bitmap data
        val f: File = File(ctx.getCacheDir(), "profile")
        f.createNewFile()
        //Convert bitmap to byte array
        val bos: ByteArrayOutputStream = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        //write the bytes in file
        val fos: FileOutputStream = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        return f
    }

}
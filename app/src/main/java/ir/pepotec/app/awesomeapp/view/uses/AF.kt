package ir.pepotec.app.awesomeapp.view.uses

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.ServerRes
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.app.PendingIntent
import android.content.Intent
import ir.pepotec.app.awesomeapp.view.student.chat.ServiceChat


class AF {

    fun convertBitMapToFile(b: Bitmap, ctx: Context, child: String): File {
        //create a file to write bitmap data
/*        val fileList = ctx.cacheDir.listFiles()
        for (o in fileList)
            if (o.user_name == child)
                o.delete()*/
        val f: File = File(ctx.cacheDir, child)
        f.createNewFile()
        //Convert bitmap to byte array
        val bos: ByteArrayOutputStream = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        //write the bytes in file
        val fos: FileOutputStream = FileOutputStream(f, false)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        return f
    }

    @SuppressLint("CheckResult")
    fun setImage(img: ImageView, data: Any?, cache:Boolean = false) {
        val rOption:RequestOptions = RequestOptions()
        if(!cache)
        {
            rOption.diskCacheStrategy(DiskCacheStrategy.NONE)
            rOption.skipMemoryCache(true)
        }
        rOption.error(R.drawable.ic_broken_img)
        rOption.centerCrop()
        Glide.with(App.instanse)
            .load(data)
            .apply(rOption)
            .into(img)
    }

    fun serverMessage(code: Int): String = when (code) {
        ServerRes.ok -> "با موفقیت انجام شد."
        ServerRes.error -> "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
        ServerRes.badReq -> "درخواست اشتباه!"
        else -> "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!"
    }

    fun setChatAlarm() {
        val intent = Intent(App.instanse, ServiceChat::class.java)
        val pendingIntent = PendingIntent.getService(
            App.instanse, 0, intent, 0
        )

        val alarmManager = App.instanse.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            60000,
            pendingIntent
        )

    }

}
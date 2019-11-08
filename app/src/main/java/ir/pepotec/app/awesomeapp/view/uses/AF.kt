package ir.pepotec.app.awesomeapp.view.uses

import android.annotation.SuppressLint
import android.app.Activity
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
import android.graphics.Point
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.MediaStoreSignature
import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.chat.ServiceChat


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


    fun setImage(img: ImageView, url: String, imgId: Int, new:Boolean, cache: Boolean = false) {
        val rOption = RequestOptions()
        if (/*!cache*/true) {
            rOption.diskCacheStrategy(DiskCacheStrategy.NONE)
            rOption.skipMemoryCache(true)
        }

        Glide.with(App.instanse)
            .load(ApiClient.serverAddress + url + "?phone=${UserDb().getUserPhone()}&apiCode=${UserDb().getUserApiCode()}&imgId=$imgId"+if(new) "&new=1" else "")
            .apply(rOption)
            .centerCrop()
            .placeholder(R.drawable.ic_glide_place_holder)
            .into(img)
    }

    fun setImageWithBounds(img: ImageView, url: String, imgId: Int, new:Boolean, cache: Boolean = false) {

        val rOption = RequestOptions()
        if (!cache) {
            rOption.diskCacheStrategy(DiskCacheStrategy.NONE)
            rOption.skipMemoryCache(true)
        }

        Glide.with(App.instanse)
            .asBitmap()
            .load(ApiClient.serverAddress + url + "?phone=${UserDb().getUserPhone()}&apiCode=${UserDb().getUserApiCode()}&imgId=$imgId"+if(new) "&new=1" else "")
            .apply(rOption)
            .centerCrop()
            .placeholder(R.drawable.ic_glide_place_holder)
            .into(object:SimpleTarget<Bitmap>(){
                override fun onResourceReady(b: Bitmap, transition: Transition<in Bitmap>?) {
                    val div = b.height.toFloat() / b.width
                    val h = div * img.width
                    img.apply {
                        layoutParams.width = img.width
                        layoutParams.height = h.toInt()
                        setImageBitmap(b)
                    }
                }
            })
    }

    fun setImage(img: ImageView, path: String, cache: Boolean = false) {
        val rOption = RequestOptions()
        if (!cache) {
            rOption.diskCacheStrategy(DiskCacheStrategy.NONE)
            rOption.skipMemoryCache(true)
        }
        Glide.with(App.instanse as MyActivity)
            .load(path)
            .apply(rOption)
            .placeholder(R.drawable.ic_glide_place_holder)
            .into(img)
    }

    fun serverMessage(code: Int): String = when (code) {
        ServerRes.ok -> "با موفقیت انجام شد."
        ServerRes.error -> "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
        ServerRes.badReq -> "درخواست اشتباه!"
        else -> "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!"
    }

}
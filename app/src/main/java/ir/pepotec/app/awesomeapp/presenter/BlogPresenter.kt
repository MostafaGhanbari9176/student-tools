package ir.pepotec.app.awesomeapp.presenter

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.blog.Blog
import ir.pepotec.app.awesomeapp.model.blog.BlogData
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class BlogPresenter(private val listener: BlogResult) {

    interface BlogResult {
        fun blogResult(ok: Boolean, message: String, data: ArrayList<BlogData>?) {}
        fun blogImgData(data: ByteArray?) {}
    }

    val phone = UserDb().getUserPhone()
    val ac = UserDb().getUserApiCode()

    fun add(message: String) {
        Blog(object : Blog.BlogRes {
            override fun response(res: ServerRes?) {
                listener.blogResult(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    null
                )
            }
        }).add(phone, ac, message)
    }

    fun getData(step: Int, num: Int, dataMethod: String) {
        Blog(object : Blog.BlogRes {
            override fun response(res: ServerRes?) {
                val data = ArrayList<BlogData>()
                res?.let {
                    for (o in it.data)
                        data.add(Gson().fromJson(o, BlogData::class.java))
                }
                listener.blogResult(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    data
                )
            }
        }).apply {
            when (dataMethod) {
                "all" -> getAllData(phone, ac, num, step)
                "like" -> getPerLike(phone, ac, num, step)
            }
        }
    }

    fun changeLike(mId: Int) {
        Blog(object : Blog.BlogRes {}).changeLike(phone, ac, mId)
    }

    fun changeStatus(mId: Int, status: Int) {
        Blog(object : Blog.BlogRes {}).report(phone, ac, mId, status)
    }

    fun addWithImg(message: String, file: File) {
        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)
        val messageBody = RequestBody.create(MultipartBody.FORM, message)

        val fileBody = RequestBody.create(MediaType.parse(".jpg"), file)
        val multiData = MultipartBody.Part.createFormData("img", "img", fileBody)

        Blog(object : Blog.BlogRes {
            override fun response(res: ServerRes?) {
                listener.blogResult(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    null
                )
            }
        }).addWithImg(phoneBody, acBody, messageBody, multiData)
    }

    fun getImg(m_id: Int) {
        Blog(object : Blog.BlogRes {
            override fun imageDate(data: ByteArray?) {
                listener.blogImgData(data)
            }
        }).getImg(phone, ac, m_id)
    }
}
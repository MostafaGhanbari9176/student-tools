package ir.pepotec.app.awesomeapp.presenter

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.course.Course
import ir.pepotec.app.awesomeapp.model.course.CourseData
import ir.pepotec.app.awesomeapp.model.course.CourseListData
import ir.pepotec.app.awesomeapp.model.news.*
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF

class CoursePresenter(private val listener:Res) {

    interface Res {
        fun listData(ok:Boolean, message:String, data:ArrayList<CourseListData>?){}
        fun single(ok:Boolean, message:String, data:CourseData?){}
        fun singleList(ok:Boolean, message:String, data:ArrayList<CourseData>?){}
    }

    private val phone = UserDb().getUserPhone()
    private val apiCode = UserDb().getUserApiCode()

    fun listData(groupId:Int, num:Int, step:Int)
    {
        Course(object:Course.Res{
            override fun result(res: ServerRes?) {
                val data = ArrayList<CourseData>()
                res?.let {
                    for(o in it.data)
                    data.add(Gson().fromJson(o, CourseData::class.java))
                }
                listener.singleList(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).getListData(phone,apiCode,groupId, num, step)
    }

    fun firstListData()
    {
        Course(object: Course.Res{
            override fun result(res: ServerRes?) {
                val data = ArrayList<CourseListData>()
                res?.let {
                    for(i in it.data.indices)
                        data.add(Gson().fromJson(it.data[i], CourseListData::class.java))

                }
                listener.listData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).getFirstData(phone,apiCode)
    }

    fun special()
    {
        Course(object:Course.Res{
            override fun result(res: ServerRes?) {
                val data = ArrayList<CourseData>()
                res?.let {
                    for(o in it.data)
                        data.add(Gson().fromJson(o, CourseData::class.java))
                }
                listener.singleList(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).getSpecial(phone,apiCode)
    }

    fun get(courseId:Int)
    {
        Course(object:Course.Res{
            override fun result(res: ServerRes?) {
                var data:CourseData?=  null
                res?.let {
                        data = Gson().fromJson(it.data[0], CourseData::class.java)
                }
                listener.single(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).get(phone,apiCode, courseId)
    }

}
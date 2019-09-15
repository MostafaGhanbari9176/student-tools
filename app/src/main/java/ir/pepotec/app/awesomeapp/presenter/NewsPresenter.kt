package ir.pepotec.app.awesomeapp.presenter

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.news.*
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF

class NewsPresenter(private val listener:Res) {

    interface Res {
        fun listData(ok:Boolean, message:String, data:ArrayList<NewsListData>?, agencyData:ArrayList<AgencyData>?){}
        fun single(ok:Boolean, message:String, data:NewsData?){}
        fun singleList(ok:Boolean, message:String, data:ArrayList<NewsData>?){}
    }

    private val phone = UserDb().getUserPhone()
    private val apiCode = UserDb().getUserApiCode()

    fun listData(groupId:Int, num:Int, step:Int)
    {
        News(object:News.Res{
            override fun result(res: ServerRes?) {
                val data = ArrayList<NewsData>()
                res?.let {
                    for(o in it.data)
                    data.add(Gson().fromJson(o, NewsData::class.java))
                }
                listener.singleList(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).getListData(phone,apiCode,groupId, num, step)
    }

    fun firstListData()
    {
        News(object:News.Res{
            override fun result(res: ServerRes?) {
                val data = ArrayList<NewsListData>()
                var agencyData = ArrayList<AgencyData>()
                res?.let {
                    for(i in it.data.indices)
                    {
                        if(i == 0)
                            agencyData = Gson().fromJson(it.data[0], AgencyList::class.java).agencyData
                        else
                            data.add(Gson().fromJson(it.data[i], NewsListData::class.java))
                    }
                }
                listener.listData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data, agencyData)
            }
        }).getFirstData(phone,apiCode)
    }

}
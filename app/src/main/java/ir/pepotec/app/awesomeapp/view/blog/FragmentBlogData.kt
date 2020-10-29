package ir.pepotec.app.awesomeapp.view.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.model.blog.BlogData
import ir.pepotec.app.awesomeapp.presenter.BlogPresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment

class FragmentBlogData : MyFragment(), AdapterBlog.AdapterBlogEvent {

    var dataMethode = "all"
    private var adapter: AdapterBlog? = null
    private var step = 0
    private var num = 10
    private var endOfData = false
    private val progress = DialogProgress { getData() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return RecyclerView(ctx).apply {
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getData()
    }

    private fun getData() {
        if (endOfData)
            return
        progress.show()
        BlogPresenter(object : BlogPresenter.BlogResult {
            override fun blogResult(ok: Boolean, message: String, data: ArrayList<BlogData>?) {
                progress.cancel()
                if (ok) {
                    setUpRV(data!!)
                    if (data.size == 0)
                        endOfData = true
                } else {
                    toast(message)
                    endOfData = true
                }
            }
        }).getData(++step, num, dataMethode)

    }

    private fun setUpRV(data: ArrayList<BlogData>) {
        adapter?.let {
            it.data.addAll(data)
            it.notifyItemRangeChanged(it.itemCount - 1, it.data.size)
        } ?: run {
            adapter = AdapterBlog(data, this)
            (view as RecyclerView).apply {
                layoutManager = LinearLayoutManager(ctx)
                adapter = this@FragmentBlogData.adapter
            }
        }
    }

    override fun report(position: Int) {
    }

    override fun like(position: Int) {
        adapter?.let {
            val v = it.data[position]
            v.liked = !(it.data[position].liked)
            if (v.liked)
                v.like_num++
            else
                v.like_num--
            it.notifyItemChanged(position)
            BlogPresenter(object : BlogPresenter.BlogResult {

            }).changeLike(it.data[position].m_id)
        }
    }

    override fun endOfList() {
        getData()
    }

}
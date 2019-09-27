package ir.pepotec.app.awesomeapp.view.blog

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.blog.Blog
import ir.pepotec.app.awesomeapp.model.blog.BlogData
import ir.pepotec.app.awesomeapp.presenter.BlogPresenter
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_blog.view.*
import kotlinx.android.synthetic.main.item_blog_expand.view.*
import kotlinx.android.synthetic.main.item_blog_img.view.*
import kotlinx.android.synthetic.main.item_blog_img_expand.view.*

class AdapterBlog(val data: ArrayList<BlogData>, private val listener: AdapterBlogEvent) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface AdapterBlogEvent {
        fun report(position: Int)
        fun like(position: Int)
        fun endOfList()
    }

    class MainHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(data: BlogData, adapter: AdapterBlog, position: Int) {
            itemView.apply {
                adapter.setUserImg(imgItemBlog, data.user_id)
                txtBlog.text = data.m_text
                txtDateBlog.text = data.m_date + " " + data.m_time
                txtSIdItemBlog.text = data.s_id
                btnExpandItemBlog.setOnClickListener {
                    data.expand = true
                    adapter.notifyItemChanged(position)
                }
                txtLikeBlog.apply {
                    text = "${data.like_num}"
                    val d: Drawable? = ContextCompat.getDrawable(
                        App.instanse,
                        if (data.liked) R.drawable.ic_favorite else R.drawable.ic_dislike
                    )
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, d, null)
                    setOnClickListener {
                        adapter.listener.like(position)
                    }
                }
                btnReportBlog.setOnClickListener {
                    DialogBlogReport(data.m_id)
                }
            }
        }
    }

    class ExpandHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(data: BlogData, adapter: AdapterBlog, position: Int) {
            itemView.apply {
                adapter.setUserImg(imgItemBlogExpand, data.user_id)
                txtBlogExpand.text = data.m_text
                txtDateBlogExpand.text = data.m_date + " " + data.m_time
                txtSIdItemBlogExpand.text = data.s_id
                btnCollapseItemBlog.setOnClickListener {
                    data.expand = false
                    adapter.notifyItemChanged(position)
                }
                txtLikeBlogExpand.apply {
                    text = "${data.like_num}"
                    val d: Drawable? = ContextCompat.getDrawable(
                        App.instanse,
                        if (data.liked) R.drawable.ic_favorite else R.drawable.ic_dislike
                    )
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, d, null)
                    setOnClickListener {
                        adapter.listener.like(position)
                    }
                }
                btnReportBlogExpand.setOnClickListener {
                    DialogBlogReport(data.m_id)
                }
            }
        }
    }

    class ImageHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(data: BlogData, adapter: AdapterBlog, position: Int) {
            itemView.apply {
                adapter.setUserImg(imgItemBlogImg, data.user_id)
                txtBlogImg.text = data.m_text
                txtDateBlogImg.text = data.m_date + " " + data.m_time
                txtSIdItemBlogImg.text = data.s_id
                btnExpandItemBlogImg.setOnClickListener {
                    data.expand = true
                    adapter.notifyItemChanged(position)
                }
                txtLikeBlogImg.apply {
                    text = "${data.like_num}"
                    val d: Drawable? = ContextCompat.getDrawable(
                        App.instanse,
                        if (data.liked) R.drawable.ic_favorite else R.drawable.ic_dislike
                    )
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, d, null)
                    setOnClickListener {
                        adapter.listener.like(position)
                    }
                }
                btnReportBlogImg.setOnClickListener {
                    DialogBlogReport(data.m_id)
                }
                adapter.setImage(imgPostBlog, data.m_id, data.expand)
            }
        }
    }

    class ImageExpandHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(data: BlogData, adapter: AdapterBlog, position: Int) {
            itemView.apply {
                adapter.setUserImg(imgItemBlogImgExpand, data.user_id)
                txtBlogImgExpand.text = data.m_text
                txtDateBlogImgExpand.text = data.m_date + " " + data.m_time
                txtSIdItemBlogImgExpand.text = data.s_id
                btnCollapseItemBlogImg.setOnClickListener {
                    data.expand = false
                    adapter.notifyItemChanged(position)
                }
                txtLikeBlogImgExpand.apply {
                    text = "${data.like_num}"
                    val d: Drawable? = ContextCompat.getDrawable(
                        App.instanse,
                        if (data.liked) R.drawable.ic_favorite else R.drawable.ic_dislike
                    )
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, d, null)
                    setOnClickListener {
                        adapter.listener.like(position)
                    }
                }
                btnReportBlogImgExpand.setOnClickListener {
                    DialogBlogReport(data.m_id)
                }
                adapter.setImage(imgPostBlogExpand, data.m_id, data.expand)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ExpandHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_blog_expand, parent, false))
            2 -> ImageHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_blog_img, parent, false))
            3 -> ImageExpandHolder(
                LayoutInflater.from(App.instanse).inflate(
                    R.layout.item_blog_img_expand,
                    parent,
                    false
                )
            )
            else -> MainHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_blog, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == itemCount - 1)
            listener.endOfList()
        when (getItemViewType(position)) {
            0 -> (holder as MainHolder).onBind(data[position], this@AdapterBlog, position)
            1 -> (holder as ExpandHolder).onBind(data[position], this@AdapterBlog, position)
            2 -> (holder as ImageHolder).onBind(data[position], this@AdapterBlog, position)
            3 -> (holder as ImageExpandHolder).onBind(data[position], this@AdapterBlog, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val d = data[position]
        return if (d.file_id == 0 && d.expand) 1
        else if (d.file_id == 0 && !d.expand) 0
        else if (d.file_id != 0 && !d.expand) 2
        else 3
    }

    private fun setUserImg(v: ImageView, userId: Int) {
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun studentImgData(data: ByteArray?) {
                data?.let {
                    // val b: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                    // imgItemBlog.setImageBitmap(b)
                }
            }
        }).downOtherImg(userId)
    }

    private fun setImage(v: ImageView, mId: Int, expand:Boolean) {
        AF().setImageWithBounds(v, Blog.baseUrl+"img", mId, expand, false)
    }

    override fun getItemCount(): Int = data.size
}
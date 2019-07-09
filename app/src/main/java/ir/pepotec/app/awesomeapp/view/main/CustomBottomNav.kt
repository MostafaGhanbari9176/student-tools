package ir.pepotec.app.awesomeapp.view.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.pepotec.app.awesomeapp.R

class CustomBottomNav : LinearLayout {

    constructor(ctx: Context, attr: AttributeSet, defStyleAttr: Int) : super(ctx, attr, defStyleAttr)
    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr)
    constructor(ctx: Context) : super(ctx)

    private lateinit var mPath: Path
    private lateinit var mPath2: Path
    private lateinit var mPaint: Paint
    private lateinit var mPaint2: Paint
    var listener: OnClickListener? = null

    init {
        mPaint = Paint()
        mPaint2 = Paint()
        mPath = Path()
        mPath2 = Path()
        mPaint.apply {
            style = Paint.Style.FILL_AND_STROKE
            color = ContextCompat.getColor(context, R.color.colorPrimary)
        }
        mPaint2.apply {
            style = Paint.Style.FILL_AND_STROKE
            color = ContextCompat.getColor(context, R.color.colorAccent)
        }
        setBackgroundColor(Color.TRANSPARENT)
        gravity = Gravity.CENTER_VERTICAL
        addChilds()
    }

    private fun addChilds() {
        //addView(createImg(0.06f, 0, R.drawable.ic_more))
        addView(createTXT(1f, 1, "خانه", R.drawable.ic_home))
        addView(createTXT(1f, 2, "جستوجو", R.drawable.ic_search))
        addView(createFAB())
        addView(createTXT(1f, 3, "اخبار", R.drawable.ic_subtitles))
        addView(createTXT(1f, 4, "تبلیغات", R.drawable.ic_subtitles))
        //addView(createImg(5,  R.drawable.ic_more))
    }

    private fun createTXT(w: Float, index: Int, ttl: String, @DrawableRes imgId: Int): TextView {

        val param = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
        param.weight = w
        return TextView(context).apply {
            //setPadding(8, 8, 8,0)
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.design_bottom_navigation_text_size)
            )
            setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(context, imgId),
                null,
                null
            )
            setTextColor(context.resources.getColor(R.color.light))
            text = ttl
            layoutParams = param
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            setOnClickListener { listener?.onClick(this) }
        }
    }

    private fun createImg(w: Float, index: Int, @DrawableRes imgId: Int): ImageView {
        val param = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
        param.weight = w
        return ImageView(context).apply {
            setImageDrawable(ContextCompat.getDrawable(context, imgId))
            layoutParams = param
            setOnClickListener { listener?.onClick(this) }
        }
    }

    private fun createFAB(): FloatingActionButton {
        val param =
            LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        return FloatingActionButton(context).apply {
            size = context.resources.getDimensionPixelSize(R.dimen.design_fab_size_normal)
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_android))
            layoutParams = param
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mPath.reset()
        mPath2.reset()
        mPath2.addCircle(height / 2f, height / 2f, 8 * height / 20f, Path.Direction.CW)
        mPath2.close()
        mPath2.addCircle(width - height / 2f, height / 2f, 8 * height / 20f, Path.Direction.CW)
        mPath2.close()
        mPath.addRect(height / 2f, height / 10f, width - height / 2f, 9 * height / 10f, Path.Direction.CCW)
        mPath.close()
        mPath.addCircle(width / 2f, height / 2f, height / 2f, Path.Direction.CCW)
        mPath.close()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath2, mPaint)
        canvas?.drawPath(mPath, mPaint)
/*        val param = LayoutParams(LayoutParams.MATCH_PARENT, 500)
        layoutParams = param
        requestLayout()*/
    }
}
package ir.pepotec.app.awesomeapp.view.student.profile

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.activity_student.view.*

class ProfileHead : LinearLayout {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr)
    constructor(ctx: Context, attr: AttributeSet, def: Int) : super(ctx, attr, def)

    private var circleAnimate: ValueAnimator? = null
    private var img: ImageView? = null
    private val drawPath = Path()
    private val transPaint = Paint()
    private val backPaint = Paint()
    private val drawPaint = Paint()

    init {
        drawPaint.apply {
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.colorPrimary)
        }
        backPaint.apply {
            color = ContextCompat.getColor(context, R.color.backGround)
            style = Paint.Style.FILL
        }
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        setWillNotDraw(false)
        transPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private fun startAimate() {
        circleAnimate = ValueAnimator.ofFloat(0f, 125 * width / 1000f).apply {
            startDelay = 300
            duration = 250
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                this@ProfileHead.invalidate()
            }
            start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawPath.apply {
            reset()
            moveTo(0f, 0f)
            lineTo(38 * width / 100f, 85 * height / 100f)
            cubicTo(
                46 * width / 100f,
                height.toFloat(),
                54 * width / 100f,
                height.toFloat(),
                62 * width / 100f,
                85 * height / 100f
            )
            lineTo(width.toFloat(), 0f)
            close()
        }
        val imgX = width / 2f - 125 * width / 1000f
        val imgY = 6 * height / 10f - 125 * width / 1000f
        val imgWidth = 125 * width / 500
        img?.apply {
            layoutParams.width = imgWidth
            layoutParams.height = imgWidth
            requestLayout()
            x = imgX
            y = imgY
        }
        startAimate()

}

override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.drawRect(0f,0f, width.toFloat(), height.toFloat(), backPaint)
    canvas?.drawPath(drawPath, drawPaint)
    canvas?.drawCircle(width / 2f, 6 * height / 10f, (circleAnimate?.animatedValue) as Float, transPaint)
}

fun initialize(img: ImageView) {
    this@ProfileHead.img = img
    val p = Point()
    (context as Activity).windowManager.defaultDisplay.getRealSize(p)
    layoutParams.height = 4 * (p.x) / 10
    val v = LayoutInflater.from(context).inflate(R.layout.activity_student, null, false)
    y = (v.tabLayoutStudent.height).toFloat()
    requestLayout()
}

}
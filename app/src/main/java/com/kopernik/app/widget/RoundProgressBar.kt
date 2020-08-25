package com.kopernik.app.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.kopernik.R


/**
 *
 * @ProjectName:    Kopernik
 * @Package:        com.kopernik.app.widget
 * @ClassName:      RoundProgressBar
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/8/25 9:56 AM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/8/25 9:56 AM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class RoundProgressBar(context: Context, attrs: AttributeSet?, defStyle: Int) :
    View(context, attrs, defStyle) {
    /**
     * 画笔对象的引用
     */
    private val paint: Paint = Paint()
    /**
     * 中间进度数字画笔对象的引用
     */
    private val paintText: Paint = Paint()

    /**
     * 圆环的颜色
     */
    private var roundColor: Int

    /**
     * 圆环进度的颜色
     */
    private var roundProgressColor: Int

    /**
     * 中间进度百分比的字符串的颜色
     */
    private var textColor: Int

    /**
     * 中间进度百分比的字符串的字体
     */
    private var textSize: Float

    /**
     * 圆环的宽度
     */
    private var roundWidth: Float

    /**
     * 最大进度
     */
    private var max: Int

    /**
     * 当前进度
     */
    private var progress = 0

    /**
     * 是否显示中间的进度
     */
    private val textIsDisplayable: Boolean

    /**
     * 进度的风格，实心或者空心
     */
    private val style: Int

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

     override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 画最外层的大圆环
         */
        val centre = width / 2 //获取圆心的x坐标
        val radius = (centre - roundWidth / 2).toInt() //圆环的半径
         paint.color = roundColor //设置圆环的颜色
         paint.style = Paint.Style.STROKE //设置空心
         paint.strokeWidth = roundWidth //设置圆环的宽度
         paint.isAntiAlias = true //消除锯齿
        canvas.drawCircle(centre.toFloat(), centre.toFloat(), radius.toFloat(), paint) //画出圆环
        Log.e("log", centre.toString() + "")
        /**
         * 画进度百分比
         */
         paintText.strokeWidth = 0F
         paintText.style = Paint.Style.FILL //设置空心
         paintText.isAntiAlias = true //消除锯齿
         paintText.color = textColor
         paintText.textSize = textSize
         paintText.typeface = Typeface.DEFAULT_BOLD //设置字体
        val percent =
            (progress.toFloat() / max.toFloat() * 100).toInt() //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        val textWidth: Float =
            paintText.measureText("$percent%") //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if (textIsDisplayable && percent != 0 && style == STROKE) {
            canvas.drawText(
                "$percent%",
                centre - textWidth / 2+5,
                centre + textSize / 2-15,
                paintText
            ) //画出进度百分比
        }
        /**
         * 画圆弧 ，画圆环的进度
         */

        //设置进度是实心还是空心
         paint.strokeWidth = roundWidth //设置圆环的宽度
         paint.color = roundProgressColor //设置进度的颜色
        val oval = RectF(
            (centre - radius).toFloat(), (centre - radius).toFloat(), (centre
                    + radius).toFloat(), (centre + radius).toFloat()
        ) //用于定义的圆弧的形状和大小的界限
        when (style) {
            STROKE -> {
                paint.style = Paint.Style.STROKE
                canvas.drawArc(oval, 0f, (360 * progress / max).toFloat(), false, paint) //根据进度画圆弧
            }
            FILL -> {
                paint.style = Paint.Style.FILL_AND_STROKE
                if (progress != 0) canvas.drawArc(
                    oval,
                    0f,
                    (360 * progress / max).toFloat(),
                    true,
                    paint
                ) //根据进度画圆弧
            }
        }
    }

    @Synchronized
    fun getMax(): Int {
        return max
    }

    /**
     * 设置进度的最大值
     * @param max
     */
    @Synchronized
    fun setMax(max: Int) {
        require(max >= 0) { "max not less than 0" }
        this.max = max
    }

    /**
     * 获取进度.需要同步
     * @return
     */
    @Synchronized
    fun getProgress(): Int {
        return progress
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress
     */
    @Synchronized
    fun setProgress(progress: Int) {
        var progress = progress
        require(progress >= 0) { "progress not less than 0" }
        if (progress > max) {
            progress = max
        }
        if (progress <= max) {
            this.progress = progress
            postInvalidate()
        }
    }

    fun getCricleColor(): Int {
        return roundColor
    }

    fun setCricleColor(cricleColor: Int) {
        roundColor = cricleColor
    }

    fun getCricleProgressColor(): Int {
        return roundProgressColor
    }

    fun setCricleProgressColor(cricleProgressColor: Int) {
        roundProgressColor = cricleProgressColor
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
    }

    fun getRoundWidth(): Float {
        return roundWidth
    }

    fun setRoundWidth(roundWidth: Float) {
        this.roundWidth = roundWidth
    }

    companion object {
        const val STROKE = 0
        const val FILL = 1
    }

    init {
        val mTypedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundProgressBar
        )

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED)
        roundProgressColor =
            mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN)
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN)
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15f)
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5f)
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100)
        textIsDisplayable =
            mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true)
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0)
        mTypedArray.recycle()
    }
}
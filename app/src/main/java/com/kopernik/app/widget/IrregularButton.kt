package com.kopernik.app.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.kopernik.R


/**
 * Created by liyayu on 2018/11/9.
 * 不规则双梯形按钮  形状如下
 *  ______________ ___________
 * |             //           |
 * |   button1  //   button2  |
 * |___________//_____________|
 */
class IrregularButton : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var mStokePaint: Paint = Paint()
    //按钮描边内侧画笔（用来画未选中stoke描边以内的背景色）
    private var mLeftInnerPath: Path = Path()
    private var mRightInnerPath: Path = Path()
    //按钮背景色路径（用来画stoke描边效果，或者选中效果)
    private var mLeftBackGroundPath: Path = Path()
    private var mRightBackGroundPath: Path = Path()
    //左侧选中背景画笔
    private var mLeftSelectedBackGroundPaint: Paint = Paint()
    //右侧选中背景画笔
    private var mRightSelectedBackGroundPaint: Paint = Paint()
    //未选中背景画笔
    private var mUnselectedLeftBackGroundPaint: Paint = Paint()
    private var mUnselectedRightBackGroundPaint: Paint = Paint()
    // 画字体的画笔
    private var textPaint: Paint = Paint()
    private var textPaintSelected: Paint = Paint()

    private var shortWidth = 100f
    private var longWidth = 80f
    private var widthCaps = 20f
    private var caps = 8f
    private var stoke = 4f

    private var totalWidth = 188f
    private var totalHeight = 30f
    private var mTextSize = 20f
    private var sharpeStokeWidth = 0f//梯形尖角处描边的x方向值，需计算来保证斜边宽度一致
    private var wideStokeWidth = 0f//梯形广角处描边的x方向值，需计算来保证斜边宽度一致
    private var mLeftText = "左侧"
    private var mRightText = "右侧"
    private var textDeviationSize = 10f //字体位置偏移量

    private var isLeftSelect = false //左侧选中与否
    private var isRightSelect = false //右侧选中与否
    private var canSelect = true//是否可以选则
    private var leftImageId = -1
    private var rightImageId = -1
    private var leftBitmap:Bitmap? = null
    private var rightBitmap:Bitmap? = null

    private var leftBgNormalBitmap:Bitmap? = null
    private var righBgtNormalBitmap:Bitmap? = null
    private var leftBgDissableBitmap:Bitmap? = null
    private var righBgtDissableBitmap:Bitmap? = null

    private var selectListener: BaseBooleanListener? = null
    private lateinit var ta: TypedArray
    //抗锯齿设置
    private val mSetfil = PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG)

    private fun init(context: Context, attrs: AttributeSet?) {
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.IrregularButton)
            widthCaps = ta.getDimension(R.styleable.IrregularButton_widthCaps, 10f)
            caps = ta.getDimension(R.styleable.IrregularButton_caps, 2f)
            stoke = ta.getDimension(R.styleable.IrregularButton_stoke, 1f)
            isLeftSelect = ta.getBoolean(R.styleable.IrregularButton_isLeftSelect, false)
            isRightSelect = ta.getBoolean(R.styleable.IrregularButton_isRightSelect, false)
            mTextSize = ta.getDimension(R.styleable.IrregularButton_buttonTextSize, 14f)
            mLeftText = ta.getString(R.styleable.IrregularButton_leftText).toString()
            mRightText = ta.getString(R.styleable.IrregularButton_rightText).toString()

        } catch (e: Exception) {
            Log.e("IrregularButton", "获取资源失败")
        } finally {
            ta.recycle()
        }
        if (leftImageId != -1) {
            leftBitmap = BitmapFactory.decodeResource(getResources(),leftImageId)
        } else {
            leftBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.agree)
        }
        if (rightImageId != -1) {
            rightBitmap = BitmapFactory.decodeResource(getResources(),rightImageId)
        } else {
            rightBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.oppose)
        }

            leftBgNormalBitmap = BitmapFactory.decodeResource(resources,R.mipmap.agree_normal_icon)
            leftBgDissableBitmap = BitmapFactory.decodeResource(resources,R.mipmap.agree_dissable_icon)
            righBgtNormalBitmap = BitmapFactory.decodeResource(resources,R.mipmap.oppose_normal_icon)
            righBgtDissableBitmap = BitmapFactory.decodeResource(resources,R.mipmap.oppose_dissable_icon)


        textDeviationSize = widthCaps / 6 //根据梯形上下边长度差修改字体偏差距离
        mStokePaint.run {
            isAntiAlias = true
            color = resources.getColor(R.color.gray2)
            style = Paint.Style.FILL_AND_STROKE
        }
        //左边选中
        mLeftSelectedBackGroundPaint.run {
            isAntiAlias = true
            color = resources.getColor(R.color.referendum_normal_bg)
            style = Paint.Style.FILL_AND_STROKE
        }
        //右边选中
        mRightSelectedBackGroundPaint.run {
            isAntiAlias = true
            color = resources.getColor(R.color.red)
            style = Paint.Style.FILL_AND_STROKE
        }

        //左边未选中画笔
        mUnselectedLeftBackGroundPaint.run {
            isAntiAlias = true
            color = resources.getColor(R.color.referendum_disable_bg2)
            style = Paint.Style.FILL_AND_STROKE
        }
        mUnselectedRightBackGroundPaint.run {
            isAntiAlias = true
            color = resources.getColor(R.color.red2)
            style = Paint.Style.FILL_AND_STROKE
        }

        textPaint.run {
            color = resources.getColor(R.color.white)
            textSize = mTextSize
        }
        textPaintSelected.run {
            color = resources.getColor(R.color.white)
            textSize = mTextSize
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        totalWidth = measuredWidth.toFloat()
        totalHeight = measuredHeight.toFloat()
        /**
         * 实现描边的重要部分！！！这里保证了斜边宽度和其他边一致
         * 设控件高度为 totalHeight = A ,梯形宽度差 widthCaps =B, 描边宽度 stoke = x，
         * 那么想要计算 尖角处描边的x方向值sharpeStokeWidth = r 可以分两段计算分别是r1 r2
         * 直角长边为 l = sqrt(A*A + B*B);r1 = (B*x)/A ; r2 = (l * x)/A,
         * 所以 r = r1+r2 = ((B + l)*x)/A  wideStokeWidth = s = (A * x)/(B + l)
         */
        val l = Math.sqrt((totalHeight * totalHeight).toDouble() + (widthCaps * widthCaps).toDouble())
        sharpeStokeWidth = (((widthCaps + l) * stoke) / totalHeight).toFloat()
        wideStokeWidth = (totalHeight * stoke) / (l + widthCaps).toFloat()

        //根据控件宽度和缝隙大小计算按钮上下梯形宽度
        shortWidth = (totalWidth - caps + widthCaps) / 2
        longWidth = (totalWidth - caps - widthCaps) / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawFilter = mSetfil

        mLeftBackGroundPath.run {
            moveTo(0f, 0f)
            lineTo(longWidth, 0f)
            lineTo(shortWidth, totalHeight)
            lineTo(0f, totalHeight)
            close()
        }
        mLeftInnerPath.run {
            moveTo(stoke, stoke)
            lineTo(longWidth - sharpeStokeWidth, stoke)
            lineTo(shortWidth - wideStokeWidth, totalHeight - stoke)
            lineTo(stoke, totalHeight - stoke)
            close()
        }
        mRightBackGroundPath.run {
            moveTo(longWidth + caps, 0f)
            lineTo(shortWidth + longWidth + caps, 0f)
            lineTo(shortWidth + longWidth + caps, totalHeight)
            lineTo(shortWidth + caps, totalHeight)
            close()
        }
        mRightInnerPath.run {
            moveTo(longWidth + caps + wideStokeWidth, stoke)
            lineTo(shortWidth + longWidth + caps - stoke, stoke)
            lineTo(shortWidth + longWidth + caps - stoke, totalHeight - stoke)
            lineTo(shortWidth + caps + sharpeStokeWidth, totalHeight - stoke)
            close()
        }

        //设置抗锯齿
        mStokePaint.isAntiAlias = true
        mLeftSelectedBackGroundPaint.isAntiAlias = true
        mRightSelectedBackGroundPaint.isAntiAlias = true
        mUnselectedLeftBackGroundPaint.isAntiAlias = true
        mUnselectedRightBackGroundPaint.isAntiAlias = true
        textPaint.isAntiAlias = true
        textPaintSelected.isAntiAlias = true

        val fm = textPaint.fontMetrics
        val textHeight = fm.descent + Math.abs(fm.ascent)
        val leftTextPaint: Paint
        val rightTextPaint: Paint

        if (!isLeftSelect && !isRightSelect && canSelect) {
            leftBgNormalBitmap?.let { canvas.drawBitmap(it, 0f,0f,textPaint) }
            righBgtNormalBitmap?.let {
                canvas.drawBitmap(
                    it, null,
                    RectF(longWidth + caps,0f,shortWidth + longWidth + caps,totalHeight),textPaint)
            }
        } else if (!isLeftSelect && !isRightSelect && !canSelect) {
            leftBgDissableBitmap?.let { canvas.drawBitmap(it, 0f,0f,textPaint) }
            righBgtDissableBitmap?.let {
                canvas.drawBitmap(
                    it, null,
                    RectF(longWidth + caps,0f,shortWidth + longWidth + caps,totalHeight),textPaint)
            }
        } else {
            if (this.isLeftSelect) { //如果左侧选中
                leftBgDissableBitmap?.let { canvas.drawBitmap(it, 0f,0f,textPaint) }
                righBgtNormalBitmap?.let {
                    canvas.drawBitmap(
                        it, null,
                        RectF(longWidth + caps,0f,shortWidth + longWidth + caps,totalHeight),textPaint)
                }
            } else {
                leftBgNormalBitmap?.let { canvas.drawBitmap(it, 0f,0f,textPaint) }
                righBgtDissableBitmap?.let {
                    canvas.drawBitmap(
                        it, null,
                        RectF(longWidth + caps,0f,shortWidth + longWidth + caps,totalHeight),textPaint)
                }
            }
         }
        //画出左右按钮字体，居中再向中偏移textDeviationSize个像素
        leftBitmap?.let { canvas.drawBitmap(it, shortWidth / 2 - (rightBitmap!!.width * 2),height / 2f - rightBitmap!!.height / 2f,textPaint) }
        rightBitmap?.let { canvas.drawBitmap(it, shortWidth / 2 + longWidth + caps - (rightBitmap!!.width * 2),height / 2f - rightBitmap!!.height / 2f,textPaint) }
        canvas.drawText(mLeftText, leftBitmap!!.width + shortWidth / 2 - textPaint.measureText(mLeftText) / 2, height / 2 + textHeight / 4, textPaint)// + textDeviationSize
        canvas.drawText(mRightText, leftBitmap!!.width + shortWidth / 2 + longWidth + caps - textPaint.measureText(mRightText) / 2, height / 2 + textHeight / 4, textPaint)// - textDeviationSize
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (canSelect) {// 是否可以做选择操作
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val isLeftContains = isRegionContainPoint(mLeftBackGroundPath, x, y)
                    val isRightContains = isRegionContainPoint(mRightBackGroundPath, x, y)
                    if (isLeftContains && !isLeftSelect) {
                        selectListener?.isLeftClick(true)
//                        isLeftSelect = true
//                        isRightSelect = false
//                        invalidate()
                    }
                    if (isRightContains && !isRightSelect) {
                        selectListener?.isRightClick(true)
//                        isRightSelect = true
//                        isLeftSelect = false
//                        invalidate()
                    }

                }
            }
        }
        return true
    }

    //判断点是否在闭合path中
    private fun isRegionContainPoint(path: Path, x: Float, y: Float): Boolean {
        val rectF = RectF()
        val mRegion = Region()
        path.computeBounds(rectF, true)
        mRegion.setPath(path, Region(rectF.left.toInt(), rectF.top.toInt(),
                rectF.right.toInt(), rectF.bottom.toInt()))
        return mRegion.contains(x.toInt(), y.toInt())
    }

    fun setLeftString(text: String) {
        mLeftText = text
        invalidate()
    }
    fun setRightString(text: String) {
        mRightText = text
        invalidate()
    }
    fun setLeftSelected(b: Boolean) {
        isLeftSelect = b
        isRightSelect = !b
        invalidate()
    }

    fun setRightSelected(b: Boolean) {
        isRightSelect = b
        isLeftSelect = !b
        invalidate()
    }

    fun setAllUnSelected(b: Boolean) {
        isRightSelect = b
        isLeftSelect = b
        canSelect = b
        invalidate()
    }

    fun setOnSelectedListener(listener: BaseBooleanListener) {
        selectListener = listener
    }

    fun setTouch (b: Boolean) {
        canSelect = b
        invalidate()
    }

    interface BaseBooleanListener {
        fun isLeftClick(b: Boolean)
        fun isRightClick(b: Boolean)
    }
}
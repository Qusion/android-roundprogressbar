package com.qusion.lib_roundprogressbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class RoundProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPrimaryPaint: Paint? = null
    private var mRectF: RectF? = null
    private var mBackgroundPaint: Paint? = null
    private var mZeroProgressEnabled = false
    private var mPrimaryProgressColor = 0
    private var mBackgroundColor = 0
    private var mStrokeWidth = 0
    private var mBackgroundStrokeWidth = 0
    private var mMax = 100
    var mProgress = 0
    private var mAnimationProgress = 0f
    private var mEndCapSize = 0
    private var mEndCapVisible = false
    private var mAnimationDuration = 1000
    private var mProgressBarStyle = 0
    private var mBackgroundStyle = 0

    private var x = 0.0
    private var y = 0.0
    private var mWidth = 0
    private var mHeight = 0

    init {
        val a: TypedArray = if (attrs != null) {
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RoundProgressBar,
                0, 0
            )
        } else {
            throw IllegalArgumentException("The attributes need to be passed")
        }
        try {
            mBackgroundColor =
                a.getColor(
                    R.styleable.RoundProgressBar_background_color,
                    context.getColor(R.color.roundprogressbar_bg_color)
                )
            mPrimaryProgressColor =
                a.getColor(
                    R.styleable.RoundProgressBar_progress_color,
                    context.getColor(R.color.roundprogressbar_colorPrimary)
                )
            mZeroProgressEnabled =
                a.getBoolean(R.styleable.RoundProgressBar_zero_progress_enabled, true)
            mMax = a.getInt(R.styleable.RoundProgressBar_max, 100)
            mProgress = a.getInt(R.styleable.RoundProgressBar_progress, 0)
            mAnimationProgress = mProgress.toFloat()
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.RoundProgressBar_stroke_width, 8)
            mBackgroundStrokeWidth =
                a.getDimensionPixelSize(R.styleable.RoundProgressBar_background_stroke_width, 4)
            mEndCapSize =
                a.getDimensionPixelSize(R.styleable.RoundProgressBar_end_cap_size, 2 * mStrokeWidth)
            mEndCapVisible = a.getBoolean(R.styleable.RoundProgressBar_end_cap_visible, false)
            mAnimationDuration = a.getInteger(R.styleable.RoundProgressBar_animation_duration, 1000)
            mProgressBarStyle = a.getInteger(R.styleable.RoundProgressBar_style, 0)
            mBackgroundStyle = a.getInteger(R.styleable.RoundProgressBar_background_style, 0)
        } finally {
            a.recycle()
        }

        mBackgroundPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mBackgroundStrokeWidth.toFloat()
            color = mBackgroundColor
            strokeCap = Paint.Cap.ROUND
        }

        if (mBackgroundStyle == BackgroundStyle.DOTTED.type) {
            val path = Path().also {
                it.addCircle(0f, 0f, mBackgroundStrokeWidth.toFloat() / 2, Path.Direction.CW)
            }
            mBackgroundPaint!!.pathEffect =
                PathDashPathEffect(path, 50f, 0f, PathDashPathEffect.Style.ROTATE)
        }
        mPrimaryPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mStrokeWidth.toFloat()
            color = mPrimaryProgressColor
            strokeCap = Paint.Cap.ROUND
        }
        mRectF = RectF()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF!![paddingLeft.toFloat() + mStrokeWidth, paddingTop.toFloat() + mStrokeWidth, w - paddingRight.toFloat() - mStrokeWidth] =
            h - paddingBottom.toFloat() - mStrokeWidth
        mWidth = w
        mHeight = h
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPrimaryPaint!!.style = Paint.Style.STROKE
        mPrimaryPaint!!.color = mPrimaryProgressColor

        val full: Boolean
        val sweepAngle: Float
        val progressStartAngle: Float
        if (mProgressBarStyle == ProgressBarStyle.FULL.type) {
            full = true
            sweepAngle = 360f
            progressStartAngle = 270f
        } else {
            full = false
            sweepAngle = 180f
            progressStartAngle = 180f
        }

        //Background
        canvas.drawArc(mRectF!!, 180f, sweepAngle, false, mBackgroundPaint!!)
        //Progress
        val primarySwipeAngle = mAnimationProgress * sweepAngle / mMax
        canvas.drawArc(mRectF!!, progressStartAngle, primarySwipeAngle, false, mPrimaryPaint!!)

        // Cap
        if (mEndCapVisible) {
            val r = (height - paddingLeft * 2 - mStrokeWidth * 2) / 2
            val offset = if (full) 90 else 180
            val trad = (primarySwipeAngle - offset) * (Math.PI / 180.0)

            x = (r * cos(trad))
            y = (r * sin(trad))
            mPrimaryPaint!!.style = Paint.Style.FILL

            if (mZeroProgressEnabled || mAnimationProgress > 0) {
                canvas.drawCircle(
                    x.toFloat() + (mWidth / 2).toFloat(),
                    y.toFloat() + (mHeight / 2).toFloat(),
                    mEndCapSize.toFloat() / 2,
                    mPrimaryPaint!!
                )
            }
        }
    }

    private fun setAnimationProgress(mAnimationProgress: Float) {
        this.mAnimationProgress = mAnimationProgress
        invalidate()
    }

    var progressColor: Int
        get() = mPrimaryProgressColor
        set(mPrimaryProgressColor) {
            this.mPrimaryProgressColor = mPrimaryProgressColor
            invalidate()
        }

    override fun setBackgroundColor(mBackgroundColor: Int) {
        this.mBackgroundColor = mBackgroundColor
        mBackgroundPaint!!.color = mBackgroundColor
        invalidate()
    }

    fun getBackgroundColor(): Int {
        return mBackgroundColor
    }

    var progress: Int
        get() = mProgress
        set(mProgress) {
            var mProg = if (mProgress < mMax) mProgress else mMax
            mProg = if (mProg < 0) 0 else mProg
            if (mProg != this.mProgress) {
                if (visibility == VISIBLE) {
                    val animation = RoundProgressBarAnimation(this, mProg, ::setAnimationProgress)
                    animation.duration = mAnimationDuration.toLong()
                    this.startAnimation(animation)
                    this.mProgress = mProg
                } else {
                    this.mProgress = mProg
                }
            }
        }

    var max: Int
        get() = mMax
        set(mMax) {
            this.mMax = mMax
            invalidate()
        }

    var isZeroProgressEnabled: Boolean
        get() = mZeroProgressEnabled
        set(mZeroProgressEnabled) {
            this.mZeroProgressEnabled = mZeroProgressEnabled
            invalidate()
        }

    var endCapSize: Int
        get() = mEndCapSize
        set(mEndCapSize) {
            this.mEndCapSize = mEndCapSize
            invalidate()
        }

    var isEndCapVisible: Boolean
        get() = mEndCapVisible
        set(mEndCapVisible) {
            this.mEndCapVisible = mEndCapVisible
            invalidate()
        }

    var animationDuration: Int
        get() = mAnimationDuration
        set(mAnimationDuration) {
            this.mAnimationDuration = mAnimationDuration
            invalidate()
        }


    enum class ProgressBarStyle(val type: Int) {
        FULL(0),
        HALF(1)
    }

    enum class BackgroundStyle(val type: Int) {
        FULL(0),
        DOTTED(1)
    }
}

package com.qusion.lib_roundprogressbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class RoundProgressBar : View {
    private var mPrimaryPaint: Paint? = null
    private var mRectF: RectF? = null
    private var mBackgroundPaint: Paint? = null
    private var mZeroProgressEnabled = false
    private var mPrimaryProgressColor = 0
    private var mBackgroundColor = 0
    private var mStrokeWidth = 0
    private var mMax = 100
    private var mProgress = 0
    private var mAnimationProgress = 0f
    private var mEndCapsSize = 0
    private var mEndCapsVisible = false
    private var x = 0.0
    private var y = 0.0
    private var mWidth = 0
    private var mHeight = 0

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
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
                    R.styleable.RoundProgressBar_backgroundColor,
                    context.getColor(R.color.roundprogressbar_bg_color)
                )
            mPrimaryProgressColor =
                a.getColor(
                    R.styleable.RoundProgressBar_progressColor,
                    context.getColor(R.color.roundprogressbar_colorPrimary)
                )
            mZeroProgressEnabled =
                a.getBoolean(R.styleable.RoundProgressBar_zeroProgressEnabled, true)
            mMax = a.getInt(R.styleable.RoundProgressBar_max, 100)
            mProgress = a.getInt(R.styleable.RoundProgressBar_progress, 0)
            mAnimationProgress = mProgress.toFloat()
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.RoundProgressBar_strokeWidth, 8)
            mEndCapsSize = a.getDimensionPixelSize(R.styleable.RoundProgressBar_endCapsSize, 4)
            mEndCapsVisible = a.getBoolean(R.styleable.RoundProgressBar_endCapsVisible, true)
        } finally {
            a.recycle()
        }
        mBackgroundPaint = Paint()
        mBackgroundPaint!!.isAntiAlias = true
        mBackgroundPaint!!.style = Paint.Style.STROKE
        mBackgroundPaint!!.strokeWidth = mStrokeWidth.toFloat() / 4
        mBackgroundPaint!!.color = mBackgroundColor
        mPrimaryPaint = Paint()
        mPrimaryPaint!!.isAntiAlias = true
        mPrimaryPaint!!.style = Paint.Style.STROKE
        mPrimaryPaint!!.strokeWidth = mStrokeWidth.toFloat()
        mPrimaryPaint!!.color = mPrimaryProgressColor
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
        // for drawing a full progress .. The background circle
        canvas.drawArc(mRectF!!, 0f, 360f, false, mBackgroundPaint!!)
        // for drawing a main progress circle
        val primarySwipeAngle = mAnimationProgress * 360 / mMax
        canvas.drawArc(mRectF!!, 270f, primarySwipeAngle, false, mPrimaryPaint!!)
        // for cap of primary progress
        val r = (height - paddingLeft * 2 - mStrokeWidth * 2) / 2
        val trad = (primarySwipeAngle - 90) * (Math.PI / 180.0)
        x = (r * cos(trad))
        y = (r * sin(trad))
        mPrimaryPaint!!.style = Paint.Style.FILL
        if (mEndCapsVisible) {
            if (mZeroProgressEnabled || mAnimationProgress > 0) {
                canvas.drawCircle(
                    (mWidth / 2).toFloat(),
                    mStrokeWidth.toFloat(),
                    mEndCapsSize.toFloat(),
                    mPrimaryPaint!!
                )
                canvas.drawCircle(
                    x.toFloat() + (mWidth / 2).toFloat(),
                    y.toFloat() + (mHeight / 2).toFloat(),
                    mEndCapsSize.toFloat(),
                    mPrimaryPaint!!
                )
            }
        }
    }

    override fun setBackgroundColor(mBackgroundColor: Int) {
        this.mBackgroundColor = mBackgroundColor
        invalidate()
    }

    var progressColor: Int
        get() = mPrimaryProgressColor
        set(mPrimaryProgressColor) {
            this.mPrimaryProgressColor = mPrimaryProgressColor
            invalidate()
        }

    var progress: Int
        get() = mProgress
        set(mProgress) {
            var mProg = if (mProgress < mMax) mProgress else mMax
            mProg = if (mProg < 0) 0 else mProg
            if (mProg != this.mProgress) {
                val animation = RoundProgressBarAnimation(this, mProg)
                animation.duration = 1000
                this.startAnimation(animation)
                this.mProgress = mProg
            }

        }

    var animationProgress: Float
        get() = mAnimationProgress
        set(mAnimationProgress) {
            this.mAnimationProgress = mAnimationProgress
            invalidate()
        }

    fun getBackgroundColor(): Int {
        return mBackgroundColor
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
}
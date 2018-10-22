package com.agrawalsuneet.dotsloader.basicviews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.agrawalsuneet.dotsloader.R

/**
 * Created by suneet on 12/29/17.
 */
open class CircularLoaderBaseView : DotsLoaderBaseView {

    protected val mNoOfDots = 8

    companion object {
        private val SIN_45 = Math.sin(45.0).toFloat()
    }

    lateinit var dotsYCorArr: FloatArray

    var bigCircleRadius: Int = 60
        set(bigCircleRadius) {
            field = bigCircleRadius
            initCoordinates()
        }

    constructor(context: Context) : super(context) {
        initCoordinates()
        initPaints()
    }

    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColor: Int) : super(context) {
        this.radius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.defaultColor = dotsColor

        initCoordinates()
        initPaints()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initCoordinates()
        initPaints()
        initShadowPaints()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initCoordinates()
        initPaints()
        initShadowPaints()
    }

    final override fun initAttributes(attrs: AttributeSet) {
        super.initAttributes(attrs)

        with(context.obtainStyledAttributes(attrs, R.styleable.CircularLoaderBaseView, 0, 0)) {
            bigCircleRadius = getDimensionPixelSize(R.styleable.CircularLoaderBaseView_loader_bigCircleRadius, 60)
            recycle()
        }
    }

    final override fun initCoordinates() {
        val sin45Radius = SIN_45 * this.bigCircleRadius

        dotsXCorArr = FloatArray(mNoOfDots)
        dotsYCorArr = FloatArray(mNoOfDots)

        for (i in 0 until mNoOfDots) {
            dotsYCorArr[i] = (this.bigCircleRadius + radius).toFloat()
            dotsXCorArr[i] = dotsYCorArr[i]
        }

        dotsXCorArr[1] = dotsXCorArr[1] + sin45Radius
        dotsXCorArr[2] = dotsXCorArr[2] + this.bigCircleRadius
        dotsXCorArr[3] = dotsXCorArr[3] + sin45Radius

        dotsXCorArr[5] = dotsXCorArr[5] - sin45Radius
        dotsXCorArr[6] = dotsXCorArr[6] - this.bigCircleRadius
        dotsXCorArr[7] = dotsXCorArr[7] - sin45Radius

        dotsYCorArr[0] = dotsYCorArr[0] - this.bigCircleRadius
        dotsYCorArr[1] = dotsYCorArr[1] - sin45Radius
        dotsYCorArr[3] = dotsYCorArr[3] + sin45Radius

        dotsYCorArr[4] = dotsYCorArr[4] + this.bigCircleRadius
        dotsYCorArr[5] = dotsYCorArr[5] + sin45Radius
        dotsYCorArr[7] = dotsYCorArr[7] - sin45Radius
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidth = 2 * this.bigCircleRadius + 2 * radius

        setMeasuredDimension(calWidth, calWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until mNoOfDots) {
            canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), defaultCirclePaint!!)
        }
    }
}
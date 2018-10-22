package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.R
import com.agrawalsuneet.dotsloader.basicviews.CircularLoaderBaseView
import com.agrawalsuneet.dotsloader.basicviews.LoaderContract

/**
 * Created by suneet on 12/29/17.
 */
class RotatingCircularDotsLoader : LinearLayout, LoaderContract {

    var dotsRadius: Int = 30
    var dotsColor: Int = ContextCompat.getColor(context, R.color.loader_selected)
    var bigCircleRadius: Int = 90

    var animDuration: Int = 5000

    private lateinit var circularLoaderBaseView: CircularLoaderBaseView


    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context, dotsRadius: Int, bigCircleRadius: Int, dotsColor: Int) : super(context) {
        this.dotsRadius = dotsRadius
        this.bigCircleRadius = bigCircleRadius
        this.dotsColor = dotsColor
        initView()
    }


    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initView()
    }

    override fun initAttributes(attrs: AttributeSet) {
        with(context.obtainStyledAttributes(attrs, R.styleable.RotatingCircularDotsLoader, 0, 0)) {
            dotsRadius = getDimensionPixelSize(R.styleable.RotatingCircularDotsLoader_rotatingcircular_dotsRadius, 30)

            dotsColor = getColor(R.styleable.RotatingCircularDotsLoader_rotatingcircular_dotsColor,
                    ContextCompat.getColor(context, R.color.loader_selected))

            bigCircleRadius = getDimensionPixelSize(R.styleable.RotatingCircularDotsLoader_rotatingcircular_bigCircleRadius, 90)

            animDuration = getInt(R.styleable.RotatingCircularDotsLoader_rotatingcircular_animDur, 5000)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val calWidth = 2 * bigCircleRadius + 2 * dotsRadius
        val calHeight = calWidth

        setMeasuredDimension(calWidth, calHeight)
    }

    private fun initView() {
        removeAllViews()
        removeAllViewsInLayout()

        circularLoaderBaseView = CircularLoaderBaseView(context, dotsRadius, bigCircleRadius, dotsColor)

        addView(circularLoaderBaseView)

        val loaderView = this

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startLoading()

                val vto = loaderView.viewTreeObserver
                vto.removeOnGlobalLayoutListener(this)
            }
        })

    }

    private fun startLoading() {

        val rotationAnim = getRotateAnimation()
        circularLoaderBaseView.startAnimation(rotationAnim)
    }

    private fun getRotateAnimation() = RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = animDuration.toLong()
        fillAfter = true
        repeatCount = Animation.INFINITE
        repeatMode = Animation.RESTART
        interpolator = LinearInterpolator()
    }
}
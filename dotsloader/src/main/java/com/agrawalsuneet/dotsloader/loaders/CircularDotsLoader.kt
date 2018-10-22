package com.agrawalsuneet.dotsloader.loaders

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.agrawalsuneet.dotsloader.basicviews.CircularLoaderBaseView

/**
 * Created by ballu on 04/07/17.
 */

class CircularDotsLoader : CircularLoaderBaseView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawCircle(canvas)

        if (shouldAnimate) {
            postDelayed({
                if (System.currentTimeMillis() - logTime >= animDur) {

                    selectedDotPos++

                    if (selectedDotPos > mNoOfDots) {
                        selectedDotPos = 1
                    }

                    invalidate()
                    logTime = System.currentTimeMillis()
                }
            }, animDur.toLong())
        }
    }

    private fun drawCircle(canvas: Canvas) {
        val firstShadowPos = if (selectedDotPos == 1) 8 else selectedDotPos - 1
        val secondShadowPos = if (firstShadowPos == 1) 8 else firstShadowPos - 1

        for (i in 0 until mNoOfDots) {
            when {
                i + 1 == selectedDotPos -> canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), selectedCirclePaint!!)
                showRunningShadow && i + 1 == firstShadowPos -> canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), firstShadowPaint)
                showRunningShadow && i + 1 == secondShadowPos -> canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), secondShadowPaint)
                else -> canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], radius.toFloat(), defaultCirclePaint!!)
            }

        }
    }
}

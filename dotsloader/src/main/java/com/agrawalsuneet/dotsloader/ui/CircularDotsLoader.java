package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;

import com.agrawalsuneet.dotsloader.R;
import com.agrawalsuneet.dotsloader.utils.Helper;

/**
 * Created by ballu on 04/07/17.
 */

public class CircularDotsLoader extends DotsLoader {

    private final int mNoOfDots = 8;
    private final float SIN_45 = 0.7071f;

    private int mBigCircleRadius = 60;

    private boolean showRunningShadow = false;
    protected float[] dotsYCorArr;

    private Paint firstShadowPaint, secondShadowPaint;

    public CircularDotsLoader(Context context) {
        super(context);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initValues();
    }

    @Override
    protected void initAttributes(AttributeSet attrs) {
        super.initAttributes(attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularDotsLoader, 0, 0);

        this.mBigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CircularDotsLoader_loader_bigCircleRadius, 60);
        this.showRunningShadow = typedArray.getBoolean(R.styleable.CircularDotsLoader_loader_showRunningShadow, true);

        typedArray.recycle();

        initValues();
    }

    @Override
    protected void initValues() {
        //selectedDotPos = 5;

        float sin45Radius = SIN_45 * mBigCircleRadius;

        dotsXCorArr = new float[mNoOfDots];
        dotsYCorArr = new float[mNoOfDots];

        for (int i = 0; i < mNoOfDots; i++) {
            dotsXCorArr[i] = dotsYCorArr[i] = mBigCircleRadius + mRadius;
        }

        dotsXCorArr[1] = dotsXCorArr[1] + sin45Radius;
        dotsXCorArr[2] = dotsXCorArr[2] + mBigCircleRadius;
        dotsXCorArr[3] = dotsXCorArr[3] + sin45Radius;

        dotsXCorArr[5] = dotsXCorArr[5] - sin45Radius;
        dotsXCorArr[6] = dotsXCorArr[6] - mBigCircleRadius;
        dotsXCorArr[7] = dotsXCorArr[7] - sin45Radius;

        /*dotsYCorArr[0] = dotsYCorArr[0] + mBigCircleRadius;
        dotsYCorArr[1] = dotsYCorArr[1] + sin45Radius;
        dotsYCorArr[3] = dotsYCorArr[3] - sin45Radius;

        dotsYCorArr[4] = dotsYCorArr[4] - mBigCircleRadius;
        dotsYCorArr[5] = dotsYCorArr[5] - sin45Radius;
        dotsYCorArr[7] = dotsYCorArr[7] + sin45Radius;*/

        dotsYCorArr[0] = dotsYCorArr[0] - mBigCircleRadius;
        dotsYCorArr[1] = dotsYCorArr[1] - sin45Radius;
        dotsYCorArr[3] = dotsYCorArr[3] + sin45Radius;

        dotsYCorArr[4] = dotsYCorArr[4] + mBigCircleRadius;
        dotsYCorArr[5] = dotsYCorArr[5] + sin45Radius;
        dotsYCorArr[7] = dotsYCorArr[7] - sin45Radius;

        //init paints for drawing dots
        defaultCirclePaint = new Paint();
        defaultCirclePaint.setAntiAlias(true);
        defaultCirclePaint.setStyle(Paint.Style.FILL);
        defaultCirclePaint.setColor(mDefaultColor);

        selectedCirclePaint = new Paint();
        selectedCirclePaint.setAntiAlias(true);
        selectedCirclePaint.setStyle(Paint.Style.FILL);
        selectedCirclePaint.setColor(mSelectedColor);

        if (showRunningShadow) {
            int firstShadowColor = Helper.adjustAlpha(mSelectedColor, 0.7f);
            int secondShadowColor = Helper.adjustAlpha(mSelectedColor, 0.5f);

            firstShadowPaint = new Paint();
            firstShadowPaint.setAntiAlias(true);
            firstShadowPaint.setStyle(Paint.Style.FILL);
            //firstShadowPaint.setColor(firstShadowColor);
            firstShadowPaint.setColor(getResources().getColor(R.color.loader_selected));

            secondShadowPaint = new Paint();
            secondShadowPaint.setAntiAlias(true);
            secondShadowPaint.setStyle(Paint.Style.FILL);
            secondShadowPaint.setColor(secondShadowColor);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = (2 * mBigCircleRadius) + (2 * mRadius);
        height = width;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);

        if (shouldAnimate) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if ((System.currentTimeMillis() - logTime) >= mAnimDur) {

                        selectedDotPos++;

                        if (selectedDotPos > mNoOfDots) {
                            selectedDotPos = 1;
                        }

                        invalidate();
                        logTime = System.currentTimeMillis();
                    }
                }
            }, mAnimDur);
        }
    }

    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < mNoOfDots; i++) {
            boolean isSelected = (i + 1 == selectedDotPos);

            canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, isSelected ? selectedCirclePaint : defaultCirclePaint);

            /*if (i + 1 == selectedDotPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, selectedCirclePaint);
            } else if (i == selectedDotPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, firstShadowPaint);
            } else {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, defaultCirclePaint);
            }*/

        }
    }

    public int getBigCircleRadius() {
        return mBigCircleRadius;
    }

    public void setBigCircleRadius(int bigCircleRadius) {
        this.mBigCircleRadius = bigCircleRadius;
        initValues();
        invalidate();
    }

    public boolean isShowRunningShadow() {
        return showRunningShadow;
    }

    public void setShowRunningShadow(boolean showRunningShadow) {
        this.showRunningShadow = showRunningShadow;
        initValues();
    }
}

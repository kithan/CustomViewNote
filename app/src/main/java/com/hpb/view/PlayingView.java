package com.hpb.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * in carousePlayList,display the flag that being played
 * @author pengbinghan
 * @date 2017/7/5
 */
public class PlayingView extends View {

    private Paint mPaint;
    private float mWidth;
    private float mHeight;
    private ValueAnimator valueAnimator;
    private float rx=4, ry=4;


    public PlayingView(Context context) {
        super(context);
    }

    public PlayingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    public PlayingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    RectF rectF1, rectF2, rectF3, rectF4, rectF5;
    float mAnimatedValue;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(0, mHeight / 2);
        rectF1 = new RectF(0, -(mAnimatedValue + mWidth / 18), mWidth / 9, mAnimatedValue + mWidth / 18);
        rectF2 = new RectF(mWidth * 2 / 9, -(mHeight / 2 - mAnimatedValue), mWidth * 3 / 9, (mHeight / 2 - mAnimatedValue));
        rectF3 = new RectF(mWidth * 4 / 9, -(mAnimatedValue + mWidth / 18), mWidth * 5 / 9, mAnimatedValue + mWidth / 18);
        rectF4 = new RectF(mWidth * 6 / 9, -(mHeight / 2 - mAnimatedValue), mWidth * 7 / 9, (mHeight / 2 - mAnimatedValue));
        rectF5 = new RectF(mWidth * 8 / 9, -(mAnimatedValue + mWidth / 18), mWidth, mAnimatedValue + mWidth / 18);
        canvas.drawRoundRect(rectF1, rx, ry, mPaint);
        canvas.drawRoundRect(rectF2, rx, ry, mPaint);
        canvas.drawRoundRect(rectF3, rx, ry, mPaint);
        canvas.drawRoundRect(rectF4, rx, ry, mPaint);
        canvas.drawRoundRect(rectF5, rx, ry, mPaint);

        canvas.restore();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
            mAnimatedValue = 0f;
            postInvalidate();
        }
    }

    private void initAnim(float endValue) {
        valueAnimator = ValueAnimator.ofFloat(0f, endValue);
        valueAnimator.setDuration(320);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public void show() {
        setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(0, 200);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initAnim(mHeight / 2 - mHeight / 18);
            valueAnimator.start();
        }
    };

    public void hide() {
        stopAnim();
        setVisibility(View.GONE);
    }


}

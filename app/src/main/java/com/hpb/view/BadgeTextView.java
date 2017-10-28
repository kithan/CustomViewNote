package com.hpb.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;


/**
 * show text+badge on top_left,top_right,bottom_left,bottom_right corner
 *
 * @author pengbinghan
 *         created at 2017/10/24
 */
public class BadgeTextView extends View {
    public BadgeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public BadgeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BadgeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private Paint mPaint;
    private String mText;
    private int mBaseLine;
    private int mViewWidth, mViewHeight;
    private static final int TEXT_COLOR_INDEX = 0;
    private static final int TEXT_SIZE_INDEX = 1;
    private static final int TEXT_INDEX = 2;
    private int mBadgeWidth, mBadgeHeight;
    private Drawable mBadge;
    private int defaultTextColor;
    private int badgeLoc;
    public static final int LOC_TOP_LEFT=0x1;
    public static final int LOC_TOP_RIGHT=0x10;
    public static final int LOC_BOTTOM_LEFT=0x11;
    public static final int LOC_BOTTOM_RIGHT=0x100;

    private void init(Context context, AttributeSet attrs) {
        DisplayMetrics displayMetrics=new DisplayMetrics();
       WindowManager windowManager=(WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.textColor
                , android.R.attr.textSize,android.R.attr.text, R.attr.badgeWidth, R.attr.badgeHeight,R.attr.badgeLoc});
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        defaultTextColor=Color.DKGRAY;
        mPaint.setColor(typedArray.getColor(TEXT_COLOR_INDEX, defaultTextColor));

        mPaint.setTextSize(typedArray.getDimensionPixelOffset(TEXT_SIZE_INDEX,36));
        mBadgeWidth = typedArray.getDimensionPixelOffset(R.styleable.BadgeTextView_badgeWidth, 70);
        mBadgeHeight = typedArray.getDimensionPixelOffset(R.styleable.BadgeTextView_badgeHeight, 24);
        badgeLoc=typedArray.getInt(R.styleable.BadgeTextView_badgeLoc,LOC_TOP_RIGHT);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mText)) {
            canvas.drawText(mText, mViewWidth / 2, mBaseLine, mPaint);
        }
        if (mBadge != null) {
            setBounds();
            mBadge.draw(canvas);
        }
    }
    private void setBounds(){
        switch (badgeLoc){
            case  LOC_TOP_LEFT:
                mBadge.setBounds(0,0,mBadgeWidth,mBadgeHeight);
                break;
            case LOC_TOP_RIGHT:
                mBadge.setBounds(mViewWidth - mBadgeWidth, 0, mViewWidth, mBadgeHeight);
                break;
            case  LOC_BOTTOM_LEFT:
                mBadge.setBounds(0,mViewHeight-mBadgeHeight,mBadgeWidth,mViewHeight);
                break;
            case LOC_BOTTOM_RIGHT:
                mBadge.setBounds(mViewWidth-mBadgeWidth,mViewHeight-mBadgeHeight,mViewWidth,mViewHeight);
        }
    }

    @Override
    protected void drawableStateChanged() {
        if(isSelected()){
            mPaint.setColor(Color.YELLOW);
        }else if(isFocused()){
            mPaint.setColor(Color.WHITE);
        }else if(isPressed()){
            mPaint.setColor(Color.YELLOW);
        }
        else{
            mPaint.setColor(defaultTextColor);
        }
        super.drawableStateChanged();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        mBaseLine = mViewHeight / 2 + (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
    }


    public void setText(String text) {
        mText = text;
        invalidate();
    }

    public void setBadge(@DrawableRes int resId) {
        mBadge = getResources().getDrawable(resId);
        invalidate();
    }


}

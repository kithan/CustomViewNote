package com.hpb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hpb on 2017/7/19.
 * 
 */
public class DrawableVerticalCenterTextView extends TextView {

    public DrawableVerticalCenterTextView(Context context) {
        this(context, null);
    }

    public DrawableVerticalCenterTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableVerticalCenterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] mDrawables=getCompoundDrawables();
        if(mDrawables!=null){
            Drawable drawableLeft=mDrawables[0];
            if(drawableLeft!=null){
                setBounds(drawableLeft);
            }
            Drawable drawableRight=mDrawables[2];
            if(drawableRight!=null){
                setBounds(drawableRight);
            }
        }
        super.onDraw(canvas);
    }
    private void setBounds(Drawable drawable){
        Paint.FontMetricsInt fm=getPaint().getFontMetricsInt();
        int textHeight=fm.bottom-fm.top;
        int drawableHeight=drawable.getIntrinsicHeight();
        int drawableTop=(textHeight-drawableHeight)>>2;
        drawable.setBounds(0,drawableTop,drawable.getIntrinsicWidth(),drawableHeight+drawableTop);
    }
}

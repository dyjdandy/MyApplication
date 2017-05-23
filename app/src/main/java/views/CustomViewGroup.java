package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 天煞冥王 on 2016/12/22.
 */

public class CustomViewGroup extends ViewGroup {
    private int w1;
    private int w2;
    private int h1;
    private int h2;

    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            if (i == 0 || i == 1) {
                w1 += params.leftMargin + child.getWidth() + params.rightMargin;
            }
            if (i == 2 || i == 3) {
                w2 += params.leftMargin + child.getWidth() + params.rightMargin;
            }
            if (i == 0 || i == 2) {
                h1 += params.topMargin + child.getHeight() + params.bottomMargin;
            }
            if (i == 1 || i == 3) {
                h2 += params.topMargin + child.getHeight() + params.bottomMargin;
            }
        }
        int wMax = Math.max(w1, w2);
        int hMax = Math.max(h1, h2);
        setMeasuredDimension(wMode == MeasureSpec.EXACTLY ? wSize : wMax,
                hMode == MeasureSpec.EXACTLY ? hSize : hMax);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int mHeight = child.getMeasuredHeight();
            int mWidth = child.getMeasuredWidth();
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int ml = 0, mt = 0, mr, mb;
            switch (i) {
                case 0:
                    ml = params.leftMargin;
                    mt = params.topMargin;
                    break;
                case 1:
                    ml = getMeasuredWidth() - mWidth - params.rightMargin;
                    mt = params.topMargin;
                    break;
                case 2:
                    ml = params.leftMargin;
                    mt = getMeasuredHeight() - mHeight - params.bottomMargin;
                    break;
                case 3:
                    ml = getMeasuredWidth() - mWidth - params.rightMargin;
                    mt = getMeasuredHeight() - mHeight - params.bottomMargin;
                    break;
            }
            mr = ml + mWidth;
            mb = mt + mHeight;
            child.layout(ml, mt, mr, mb);
        }
    }

}

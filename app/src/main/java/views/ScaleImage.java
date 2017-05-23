package views;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by 天煞冥王 on 2016/12/27.
 */

public class ScaleImage extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    private final ScaleGestureDetector scaleGestureDetector;
    private final GestureDetector gestDetctor;
    private Matrix matrix;
    private int lastPointerCount;
    private float mLastX;
    private float mLastY;
    //    private float value;
    private boolean doubleFlag = false;

    public ScaleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        gestDetctor = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                doubleFlag = !doubleFlag;
                if (doubleFlag) {
                    matrix.postScale(0.5f, 0.5f, getWidth() / 2, getHeight() / 2);
                    setImageMatrix(matrix);
                } else {
                    matrix.postScale(2f, 2f, getWidth() / 2, getHeight() / 2);
                    setImageMatrix(matrix);
                }
                return true;
            }
        });
    }


    @Override
    public void onGlobalLayout() {
        Drawable drawable =   getDrawable();
        int dw = drawable.getIntrinsicWidth();
        int dh = drawable.getIntrinsicHeight();
        matrix = new Matrix();
        matrix.postTranslate(getWidth() / 2 - dw / 2, getHeight() / 2 - dh / 2);
        setImageMatrix(matrix);
//        float[] values = new float[9];
//        matrix.getValues(values);
//        value = values[Matrix.MSCALE_X];
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        matrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
        setImageMatrix(matrix);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestDetctor.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        /**
         * 每当触摸点个数发生变化时，重置mLasX , mLastY。
         * 假如两根手指，然后pointerCount可能会变成0或1或2。
         * onTouch会一直执行....
         * MotionEvent.ACTION_DOWN一般是针对单手指
         */
        if (pointerCount != lastPointerCount) {
            Log.e("info",pointerCount+"===========11111============="+lastPointerCount);
            mLastX = x;
            mLastY = y;
        }
        lastPointerCount = pointerCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                matrix.postTranslate(dx, dy);
                setImageMatrix(matrix);
                mLastX = x;
                mLastY = y;
                Log.e("info",pointerCount+"===========222222============="+lastPointerCount);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                Log.e("info",pointerCount+"===========3333333============="+lastPointerCount);
                break;
        }
        return true;
    }

    public RectF getMatrixRect() {
        RectF rect = new RectF();
        Drawable drawable = getDrawable();
        rect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        matrix.mapRect(rect);
        return rect;
    }

}

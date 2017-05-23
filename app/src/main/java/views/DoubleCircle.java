package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by 天煞冥王 on 2016/12/19.
 */

public class DoubleCircle extends View {
    private int currentP;
    private int mFistColor;
    private int mSecondColor;
    private float circleWidth;
    private int firstColor;
    private int secondColor;
    private int speed;

    public DoubleCircle(Context context) {
        this(context, null);
    }

    public DoubleCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleCircle, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.DoubleCircle_circleWidth:
                    circleWidth = typedArray.getDimension(attr, 10.0f);
                    break;
                case R.styleable.DoubleCircle_firstColor:
                    firstColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.DoubleCircle_secondColor:
                    secondColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.DoubleCircle_speed:
                    speed = typedArray.getInteger(attr, 5);
                    break;
            }
        }
        typedArray.recycle();
        mFistColor=firstColor;
        mSecondColor=secondColor;
        currentP=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (currentP == 360) {
                            currentP = 0;
                            mFistColor = secondColor;
                            mSecondColor = firstColor;
                        }
                        postInvalidate();
                        Thread.sleep(speed);
                        currentP ++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mFistColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - circleWidth, mPaint);
        RectF rectF = new RectF();
        rectF.top = circleWidth;
        rectF.left = circleWidth;
        rectF.right = getWidth() - circleWidth;
        rectF.bottom = getHeight() - circleWidth;
        mPaint.setColor(mSecondColor);
        canvas.drawArc(rectF, -90, currentP, false, mPaint);
    }
}

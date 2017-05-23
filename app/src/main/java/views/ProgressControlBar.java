package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by 天煞冥王 on 2016/12/20.
 */

public class ProgressControlBar extends View {
    private int barCount;
    private float cWidth;
    private int fColor;
    private int sColor;
    private Bitmap bgBitmap;
    private int mCurrent=3;
    private float xDown,xUp;

    public ProgressControlBar(Context context) {
        this(context, null);
    }

    public ProgressControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressControlBar, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ProgressControlBar_barCount:
                    barCount = typedArray.getInteger(attr, 20);
                    break;
                case R.styleable.ProgressControlBar_bg:
                    int resourceId = typedArray.getResourceId(attr, 0);
                    bgBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                    break;
                case R.styleable.ProgressControlBar_cWidth:
                    cWidth = typedArray.getDimension(attr, 10.0f);
                    break;
                case R.styleable.ProgressControlBar_fColor1:
                    fColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.ProgressControlBar_sColor1:
                    sColor = typedArray.getColor(attr, Color.RED);
                    break;
            }
        }
        typedArray.recycle();
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCurrent++;
//                postInvalidate();
//            }
//        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int p = 360 / barCount;
        RectF rectF = new RectF();
        Paint paint = new Paint();
        rectF.top = cWidth;
        rectF.left = cWidth;
        rectF.right = getWidth() - cWidth;
        rectF.bottom = getHeight() - cWidth;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(cWidth);
        paint.setAntiAlias(true);
        paint.setColor(fColor);
        for (int i = 0; i < barCount; i++) {
            canvas.drawArc(rectF, i * p, p - 10, false, paint);
        }
        paint.setColor(sColor);
        for (int i = 0; i < mCurrent; i++) {
            canvas.drawArc(rectF, i * p, p - 10, false, paint);
        }
        RectF bRect = new RectF();
        bRect.top=cWidth+60;
        bRect.left=cWidth+40;
        bRect.right=getWidth()-cWidth-50;
        bRect.bottom=getHeight()-cWidth-50;
        canvas.drawBitmap(bgBitmap,null, bRect,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp=event.getY();
                if(xUp>xDown){
                    mCurrent--;
                    postInvalidate();
                }else{
                    mCurrent++;
                    postInvalidate();
                }
                break;
        }
        return true;
    }

}

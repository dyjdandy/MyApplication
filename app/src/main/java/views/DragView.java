package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 天煞冥王 on 2016/12/23.
 */

public class DragView extends View {
    private Rect rect= new Rect(10, 10, 50, 50);
    private Paint paint;

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(rect, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!rect.contains((int)x,(int)y)){
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                rect.left= (int) x;
                rect.top= (int) y;
                rect.right=rect.left+40;
                rect.bottom=rect.top+40;
                invalidate();
                break;
        }
        return true;
    }
}

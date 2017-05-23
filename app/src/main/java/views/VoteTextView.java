package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myapplication.R;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/1/7.
 * version:1.0
 * 简单的投票textview，可以根据实际情况稍作修改
 */

public class VoteTextView extends View implements View.OnClickListener {
    private final Rect bounds;
    private  int progress;
    private Paint paint;
    private int fColor;
    private int sColor;
    private String text;
    private int textColor;
    private float textSize;

    public VoteTextView(Context context) {
        this(context, null);
    }

    public VoteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VoteTextView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.VoteTextView_fColor:
                    fColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.VoteTextView_sColor:
                    sColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.VoteTextView_text:
                    text = typedArray.getString(attr);
                    break;
                case R.styleable.VoteTextView_textColor:
                    textColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.VoteTextView_textSize:
                    textSize = typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.VoteTextView_progress:
                    progress = typedArray.getInteger(attr,0);
                    break;
            }
        }
        typedArray.recycle();
        bounds = new Rect();
        paint = new Paint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int w, h;
        if (wMode == MeasureSpec.EXACTLY) {
            w = wSize;
        } else {
            w = getPaddingLeft() + bounds.width() + getPaddingRight();
        }
        if (hMode == MeasureSpec.EXACTLY) {
            h = hSize;
        } else {
            h = getPaddingBottom() + bounds.width() + getPaddingTop();
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(fColor);
        Rect rect = new Rect();
        rect.top = 0;
        rect.left = 0;
        rect.right = getWidth();
        rect.bottom = getHeight();
        canvas.drawRect(rect, paint);

        paint.setColor(sColor);
        Rect rect1 = new Rect();
        rect1.top = 0;
        rect1.left = 0;
        rect1.right = rWidth;
        rect1.bottom = getHeight();
        canvas.drawRect(rect1, paint);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        canvas.drawText(text, getWidth() / 2 - bounds.width() / 2, getHeight() / 2 + bounds.height() / 2, paint);
    }

    private int rWidth = 100;

    @Override
    public void onClick(View v) {
        rWidth += progress;
        postInvalidate();
    }
}

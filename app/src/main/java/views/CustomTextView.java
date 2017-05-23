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
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by 天煞冥王 on 2016/12/15.
 */

public class CustomTextView extends View implements View.OnClickListener {
    private final Rect bounds;
    private int textColor;
    private String textContent;
    private float textSize;
    private Paint mPaint;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.CustomTextView_textColor1:
                    textColor = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomTextView_textContent:
                    textContent = typedArray.getString(index);
                    break;
                case R.styleable.CustomTextView_textSize1:
                    textSize = typedArray.getDimension(index, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        bounds = new Rect();
        mPaint.getTextBounds(textContent, 0, textContent.length(), bounds);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        canvas.drawText(textContent, getWidth() / 2 - bounds.width() / 2, getHeight() / 2 + bounds.height() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int w , h ;
        if (widthMode == MeasureSpec.EXACTLY) {
            w = width;
        } else {
            w = getPaddingLeft() + getPaddingRight() + bounds.width();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            h = height;
        } else {
            h = getPaddingTop() + getPaddingBottom() + bounds.height();
        }
        setMeasuredDimension(w, h);
    }

    private int i=100;
    @Override
    public void onClick(View v) {
        textContent=i+++"";
        postInvalidate();
    }
}

package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by 天煞冥王 on 2016/12/16.
 */

public class ImgAndText extends View {
    private int imgId;
    private int imgType;
    private int textColor;
    private float textSize;
    private String textContent;
    private Paint mPaint;
    private Rect textBound;
    private Bitmap imgBitmap;

    public ImgAndText(Context context) {
        this(context, null);
    }

    public ImgAndText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgAndText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImgAndText, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ImgAndText_imgId:
                    imgId = typedArray.getResourceId(attr, 0);
                    break;
                case R.styleable.ImgAndText_imgType:
                    imgType = typedArray.getInt(attr, 0);
                    break;
                case R.styleable.ImgAndText_imgTextColor:
                    textColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ImgAndText_imgTextSize:
                    textSize = typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ImgAndText_imgTextContent:
                    textContent = typedArray.getString(attr);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        textBound = new Rect();
        mPaint.getTextBounds(textContent, 0, textContent.length(), textBound);
        imgBitmap = BitmapFactory.decodeResource(getResources(), imgId);
    }

    private int width, height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        width = wSize;
        if (wMode != MeasureSpec.EXACTLY) {
            int textW = getPaddingLeft() + getPaddingRight() + textBound.width();
            int imgW = getPaddingLeft() + getPaddingRight() + imgBitmap.getWidth();
            width = Math.max(textW, imgW);
        }
        height = hSize;
        if (hMode != MeasureSpec.EXACTLY) {
            height = getPaddingBottom() + getPaddingTop() + imgBitmap.getHeight() + textBound.height();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setStyle(Paint.Style.FILL);
        //字数太多了则用...代替
        if (textBound.width() > width) {
            String text = TextUtils.ellipsize(textContent, new TextPaint(mPaint), width - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(text,getPaddingLeft(),height-getPaddingBottom(),mPaint);
        } else {
            canvas.drawText(textContent, getMeasuredWidth() / 2 - textBound.width(), getMeasuredHeight() - getPaddingBottom(), mPaint);
        }

        Rect rect = new Rect();
        rect.left = getMeasuredWidth() / 2 - imgBitmap.getWidth() / 2;
        rect.right = getMeasuredWidth() / 2 + imgBitmap.getWidth() / 2;
        rect.top = getPaddingTop();
        rect.bottom = getMeasuredHeight() - getPaddingBottom() - textBound.height() - 10;
        canvas.drawBitmap(imgBitmap, null, rect, mPaint);
    }
}

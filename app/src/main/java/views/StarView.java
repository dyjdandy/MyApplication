/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package views;

/**
 * Author：邓勇军
 * English Name：Deng,The Sky Fucker!
 * Email：dandycoder@126.com
 * Date：2017/1/12
 * Version：1.0
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

import java.util.Random;

/**
 * Created by Adminis on 2016/8/7.
 */
public class StarView extends RelativeLayout {
    private static final String TAG = "StarView";
    private int mWidth;
    private int mHeight;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setImageAnim();
            mHandler.sendEmptyMessageDelayed(0,100);
        }
    };
    public StarView(Context context) {
        super(context);
    }
    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=0&&h!=0){
            this.mWidth = w;
            this.mHeight = h;
            Log.e(TAG,"mHeight="+mHeight);
            setImageAnim();
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    }

    private void setImageAnim() {
        final ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.mipmap.login);
        LayoutParams params = new LayoutParams(40, 40);
        Random random = new Random();
        params.leftMargin=random.nextInt(mWidth-40)+1;
        this.addView(iv, params);//添加到相对布局中
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animation =  ObjectAnimator.ofFloat(iv,"translationY",mHeight,0);
        animatorSet.playTogether(animation);
        animatorSet.setDuration(1000);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                iv.clearAnimation();
                removeView(iv);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                iv.clearAnimation();
                removeView(iv);
            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(!hasWindowFocus){
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
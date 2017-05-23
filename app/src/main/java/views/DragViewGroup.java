/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package views;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class DragViewGroup extends LinearLayout {

    private final ViewDragHelper viewDragHelper;
    private View view1;
    private View view2;
    private View view3;
    private int left;
    private int top;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        view1 = getChildAt(0);
        view2 = getChildAt(1);
        view3 = getChildAt(2);
        left = view2.getLeft();
        top = view2.getTop();
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child==view1||view2==child;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if(view2==releasedChild) {
                    viewDragHelper.settleCapturedViewAt(left, top);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                viewDragHelper.captureChildView(view3,pointerId);
            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

}

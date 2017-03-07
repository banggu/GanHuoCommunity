package com.scnu.bangzhu.ganhuocommunity.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 自定义下拉刷新组件，继承自原生下拉刷新控件
 * 解决与ViewPager嵌套使用的滑动冲入问题
 * Created by bangzhu on 2017/3/7.
 */

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int mTouchSlop = 0;//最小滑动距离

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            mLastXIntercept = x;
            mLastYIntercept = y;
            return false;
        } else {
            return true;
        }
        //return super.onInterceptTouchEvent(ev);
    }

//        @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercepted = false;
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        switch(ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                intercepted = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = x - mLastXIntercept;
//                int deltaY = y - mLastYIntercept;
//                if(Math.abs(deltaX) < Math.abs(deltaY)) {
//                    intercepted = false;
//                } else {
//                    if(deltaY <= 0) {
//                        intercepted = true;
//                    } else {
//                        intercepted = false;
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                intercepted = false;
//                break;
//            default:
//                break;
//        }
//        mLastXIntercept = x;
//        mLastYIntercept = y;
//        return intercepted;
//    }
}

package com.ganxin.codebase.widgets.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Description : 只允许向左滑动的viewpager  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class ScrollLeftStackViewPager extends ViewPager {
    private boolean isCanScroll = true;
    private float beforeX;
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

    public ScrollLeftStackViewPager(Context context) {
        super(context);
    }

    public ScrollLeftStackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // -----禁止右滑-------右滑：上一次坐标 < 当前坐标
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:// 按下如果‘仅’作为‘上次坐标’，不妥，因为可能存在左滑，motionValue大于0的情况（来回滑，只要停止坐标在按下坐标的右边，左滑仍然能滑过去）
                beforeX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float motionValue = ev.getX() - beforeX;
                if (motionValue > 0) {// 禁止右滑
                    return true;
                }
                beforeX = ev.getX();// 手指移动时，再把当前的坐标作为下一次的‘上次坐标’

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setViewPagerCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }

    public void setObjectForPosition(View view, int position) {
        if (mChildrenViews != null) {
            mChildrenViews.put(position, view);
        }
    }

    public void removeObjectForPosition(int position) {
        if (mChildrenViews != null && mChildrenViews.size() > 0) {
            mChildrenViews.remove(position);
        }
    }

    /**
     * 通过位置获得对应的View
     *
     * @param position
     * @return
     */
    public View findViewFromObject(int position) {
        View view = null;
        if (mChildrenViews != null && mChildrenViews.size() > 0) {
            view = mChildrenViews.get(position);
        }
        return view;
    }
}

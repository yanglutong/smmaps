package com.sm.smmap.smmap.Utils;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.animation.Interpolator;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;
/**Viewpager滑动速度
 * @description
 * @param
 * @return
 * @author lutong
 * @time 2022/4/27 17:28
 */

public class ViewPagerScroller extends Scroller{

    private int mScrollDuration = 1800; // 滑动速度

    /**
     * 设置速度速度
     *
     * @param duration
     */
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
    }

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @SuppressLint("NewApi")
    public ViewPagerScroller(Context context, Interpolator interpolator,
                             boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
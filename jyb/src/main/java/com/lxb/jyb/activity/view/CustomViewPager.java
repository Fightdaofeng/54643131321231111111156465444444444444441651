package com.lxb.jyb.activity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    
    private boolean enabled;
    
    public CustomViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.enabled = false;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        
        return false;
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        
        return false;
    }
    
    /**
     * setPagingEnabled(这里用一句话描述这个方法的作用)
     * (这里描述这个方法适用条件 – 可选)
     * @param enabled
     *void
     * @exception
     * @since  1.0.0
    */
    public void setPagingEnabled(boolean enabled) {

        this.enabled = enabled;
    }
}
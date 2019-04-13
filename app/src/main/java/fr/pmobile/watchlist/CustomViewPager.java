package fr.pmobile.watchlist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    int swipemode = 0;
    int screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenheight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean disable = false;
    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*if(neutralArea(ev.getX(),ev.getY())){
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }*/
        return !disable && super.onInterceptTouchEvent(ev);
        //return disable ? false : super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /*if(neutralArea(ev.getX(),ev.getY())){
            return false;
        } else {
            return super.onTouchEvent(ev);
        }*/
        return !disable && super.onTouchEvent(ev);
        //return super.onTouchEvent(ev);
    }

    public void disableScroll(boolean disable){
        this.disable = disable;
    }

    public boolean neutralArea(float x, float y){
        if((y>=(screenheight/4)) && swipemode == 0){
            return true;
        }/*else if((x>=(screenwidth/2)) && swipemode == 1){
            return false;
        }*/
        return false;
    }
}

package com.common.refresh.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by zou on 2016/3/21.
 */
public abstract class PullToRefreshHeaderBase extends RelativeLayout {

    public PullToRefreshHeaderBase(Context context) {
        super(context);
    }

    public PullToRefreshHeaderBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void onPullToRefresh() ;
    public abstract void onRefreshing() ;
    public abstract void onReleaseToRefresh() ;
    public abstract void onPullAnim(float scale) ;

    public abstract void onRefreshComplete();
}

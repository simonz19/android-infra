package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.util.AttributeSet;

import com.common.refresh.R;


/**
 * Created by zou on 2016/3/23.
 * 支持下拉刷新的recyclerview组合控件
 */
public class PullToRefreshCommonRecyclerView extends PullToRefreshRecyclerView {

    public PullToRefreshCommonRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshCommonRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshCommonRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getRecyclerId() {
        return R.id.recyclerview;
    }

    @Override
    protected int getResId() {
        return R.layout.recycler_pull_to_refresh_common_layout;
    }
}

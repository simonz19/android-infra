package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.common.refresh.R;


/**
 * Created by zou on 2016/3/10.
 * android自带刷新功能
 */
public abstract class SwipRefreshRecyclerView extends RefreshRecyclerView {

    protected SwipeRefreshLayout swipeRefreshLayout;

    public SwipRefreshRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public SwipRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (onRecyclerRefreshListener != null && onRecyclerRefreshListener.size() > 0) {
                onLoadMoreComplete();
                for (int i = 0; i < onRecyclerRefreshListener.size(); i++) {
                    onRecyclerRefreshListener.get(i).onRefresh();
                }
            }
        }
    };

    private void init(Context context) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    @Override
    public void onRefreshComplete() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setMode(Mode mode) {
        super.setMode(mode);
        if (mode == Mode.DISABLE || mode == Mode.LOAD_MORE)
            swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean isRefreshing() {
        return swipeRefreshLayout.isRefreshing();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}

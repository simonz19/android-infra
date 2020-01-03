package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.util.AttributeSet;

import com.common.refresh.R;
import com.common.refresh.pulltorefresh.PullToRefreshContainer;
import com.common.refresh.pulltorefresh.PullToRefreshHeaderBase;


/**
 * Created by zou on 2016/3/22.
 * 下拉刷新控件
 */
public abstract class PullToRefreshRecyclerView extends RefreshRecyclerView {

    private PullToRefreshContainer container;
    private PullToRefreshContainer.IRefreshing irefreshing = new PullToRefreshContainer.IRefreshing() {
        @Override
        public void onRefresh() {
            if (onRecyclerRefreshListener != null) {
                RecyclerViewStateUtils.setFooterViewState(getContext(), recyclerView, LoadingFooter.State.Normal, null);
                onRecyclerRefreshListener.onRefresh();
            }
        }
    };

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        container = (PullToRefreshContainer) view.findViewById(R.id.pulltorefreshcontainer);
        container.setIRefreshing(irefreshing);
    }

    /**
     * 设置头部视图
     *
     * @param view
     */
    public void setHeaderView(PullToRefreshHeaderBase view) {
        container.setHeaderLayout(view);
    }

    @Override
    public void onRefreshComplete() {
        container.onRefreshComplete();
    }

    @Override
    public void setRefreshMode(boolean supportRefresh) {
        if (!supportRefresh)
            container.setRefreshMode(PullToRefreshContainer.DONOTREFRESH);
    }
}

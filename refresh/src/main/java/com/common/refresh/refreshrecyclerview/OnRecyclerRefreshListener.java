package com.common.refresh.refreshrecyclerview;

import android.view.View;

/**
 * Created by zou on 2016/3/22.
 */
public interface OnRecyclerRefreshListener {

    void onRefresh();

    void onLoadMore(View view);
}

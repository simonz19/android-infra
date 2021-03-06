package com.common.refresh.refreshrecyclerview;

import android.view.View;

/**
 * RecyclerView/ListView/GridView 滑动加载下一页时的回调接口
 */
public interface OnScrollToEndListener {

    /**
     * 开始加载下一页
     *
     * @param view 当前RecyclerView/ListView/GridView
     */
    void onScrollToEnd(View view);
}

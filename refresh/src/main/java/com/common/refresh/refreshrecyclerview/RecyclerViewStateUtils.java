package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cundong on 2015/11/9.
 * <p/>
 * 分页展示数据时，RecyclerView的FooterView State 操作工具类
 * <p/>
 * RecyclerView一共有几种State：Normal/Loading/Error/TheEnd
 */
public class RecyclerViewStateUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param recyclerView  recyclerView
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    @Deprecated
    static void setFooterViewState(Context context, RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        LoadingFooter footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
        } else {
            footerView = new LoadingFooter(context);
            headerAndFooterAdapter.addFooterView(footerView);
            if (state != LoadingFooter.State.Normal) {
                recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
            }
        }
        footerView.setState(state);
        if (state == LoadingFooter.State.NetWorkError) {
            footerView.setOnClickListener(errorListener);
        } else {
            footerView.setOnClickListener(null);
        }
    }

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param recyclerView  recyclerView
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    static void setFooterViewState(RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener) {
        setFooterViewState(recyclerView.getContext(), recyclerView, state, errorListener);
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     */
    static LoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return LoadingFooter.State.Normal;
    }

    /**
     * 移除footerview
     *
     * @param recyclerView
     */
    static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }
        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
        if (headerAndFooterAdapter.getFooterCount() > 0) {
            headerAndFooterAdapter.removeFooterView(headerAndFooterAdapter.getFooterView());
        }
    }

}

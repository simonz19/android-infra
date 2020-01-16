package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.refresh.R;


/**
 * Created by zou on 2016/3/23.
 * 支持swip刷新的recyclerview组合控件,支持标题置顶
 */
public class SwipRefreshPinnedRecyclerView extends SwipRefreshRecyclerView {


    public SwipRefreshPinnedRecyclerView(Context context) {
        super(context);
    }

    public SwipRefreshPinnedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipRefreshPinnedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getRecyclerId() {
        return R.id.pinnedrecyclerview;
    }

    @Override
    protected int getResId() {
        return  R.layout.recycler_swip_refresh_pinned_layout;
    }

    @Override
    protected void onRecyclerScroll(RecyclerView recyclerView, int dx, int dy) {
        if( recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            ((PinnedHeaderRecyclerView)recyclerView).configureHeaderView(((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        }else if( recyclerView.getLayoutManager() instanceof GridLayoutManager){
            ((PinnedHeaderRecyclerView)recyclerView).configureHeaderView(((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        }
    }

    /**
     *  设置置顶视图
     * @param resId
     */
    public void setPinnedHeaderView(int resId){
        ((PinnedHeaderRecyclerView)recyclerView).setPinnedHeaderView(LayoutInflater.from(getContext()).inflate(resId,recyclerView,false));
    }

    /**
     *  设置置顶视图
     * @param view
     */
    public void setPinnedHeaderView(View view){
        ((PinnedHeaderRecyclerView)recyclerView).setPinnedHeaderView(view);
    }
}

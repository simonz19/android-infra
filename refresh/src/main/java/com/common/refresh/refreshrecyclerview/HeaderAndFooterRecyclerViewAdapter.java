package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

/**
 * RecyclerView.Adapter with Header and Footer
 */
public abstract class HeaderAndFooterRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MAX_VALUE;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    public HeaderAndFooterRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void addHeaderView(View header) {

        if (header == null) {
            throw new RuntimeException("header is null");
        }

        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {

        if (footer == null) {
            throw new RuntimeException("footer is null");
        }

        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }

    /**
     * 返回最后一个FopterView
     *
     * @return
     */
    public View getFooterView() {
        return getFooterCount() > 0 ? mFooterViews.get(mFooterViews.size() - 1) : null;
    }

    /**
     * 返回第一个HeaderView
     *
     * @return
     */
    public View getHeaderView() {
        return getHeaderCount() > 0 ? mHeaderViews.get(0) : null;
    }

    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooterViews.remove(view);
        this.notifyDataSetChanged();
    }

    @Override
    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    @Override
    public int getFooterCount() {
        return mFooterViews.size();
    }

    public int getDataCount() {
        return datas.size();
    }

    public boolean isHeader(int position) {
        return getHeaderCount() > 0 && position < getHeaderCount() && position >= 0;
    }

    public boolean isFooter(int position) {
        return getFooterCount() > 0 && position >= getItemCount() - getFooterCount() && position < getItemCount();
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType > TYPE_FOOTER_VIEW / 2) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return onCreateMyViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position >= getHeaderCount() && position < getHeaderCount() + getDataCount()) {
            onBindMyViewHolder(holder, position - getHeaderCount());
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    protected abstract void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position);

    protected abstract RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType);

    protected int getMyItemViewType(int position) {
        return 0;
    }

    @Override
    public final int getItemCount() {
        return getHeaderCount() + getFooterCount() + getDataCount();
    }

    @Override
    public final int getItemViewType(int position) {
        if (position < getHeaderCount()) {
            return TYPE_HEADER_VIEW + position;
        } else if (position >= getHeaderCount() + getDataCount()) {
            return TYPE_FOOTER_VIEW - (position - getHeaderCount() - getDataCount());
        } else {
            return getMyItemViewType(position - getHeaderCount());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

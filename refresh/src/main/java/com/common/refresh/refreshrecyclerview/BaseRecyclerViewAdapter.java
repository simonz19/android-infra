package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context context;
    protected List<T> datas;
    private int lastAnimatedPosition = -1;
    private int ANIMATED_ITEMS_COUNT;
    private onItemClickLitener<T> onItemClickLitener;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickLitener!=null){
                    onItemClickLitener.onItemCLick(position,holder.itemView,datas.get(position));
                }
            }
        });
    }

    public void setOnItemClickLitener(BaseRecyclerViewAdapter.onItemClickLitener<T> onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    protected void runEnterAnimation(View view, int position) {
        if (!animate() || position < getHeaderCount() || position >= getHeaderCount() + ANIMATED_ITEMS_COUNT) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(view.getContext().getResources().getDisplayMetrics().widthPixels);
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    protected boolean animate() {
        return false;
    }

    protected BaseRecyclerViewAdapter() {
        datas = new ArrayList<T>();
    }

    protected BaseRecyclerViewAdapter(Context ctx) {
        this();
        this.context = ctx;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setData(List<T> data) {
        this.datas = data;
        ANIMATED_ITEMS_COUNT = datas.size();
        notifyDataSetChanged();
    }

    public void addData(List<T> retData) {
        this.datas.addAll(retData);
        ANIMATED_ITEMS_COUNT = datas.size();
        if (animate()) {
            notifyDataSetChanged();
        } else {
            int ponitionPosition = getHeaderCount() + datas.size();
            notifyItemRangeInserted(ponitionPosition, retData.size());
        }
    }

    public int getHeaderCount() {
        return 0;
    }

    public int getFooterCount() {
        return 0;
    }

    protected void clearAnim() {
        lastAnimatedPosition = -1;
    }

    public interface onItemClickLitener<T>{
        void onItemCLick(int position, View view, T t);
    }
}

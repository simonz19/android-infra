package com.common.refresh.refreshrecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by zou on 2016/3/23.
 * 无需自定义recyclerview,单单使用此adapter就可以实现expandable效果
 */
public abstract class ExpandableRecyclerAdapter<W extends ExpandableParentItem,T extends ExpandableChlidItem> extends BaseRecyclerViewAdapter {

    private RecyclerView.LayoutManager layoutManager ;
    protected final int PARENTTYPE = 0 ;
    protected final int CHLIDTYPE = 1 ;

    public ExpandableRecyclerAdapter(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    protected OnRecyclerClickListener onRecyclerClickListener ;
    private View.OnClickListener itemCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Integer pos = LinearLayoutManager.class.cast(layoutManager).getPosition(v) ;
                if(getItemViewType(pos)==PARENTTYPE){
                    if(((ExpandableParentItem)datas.get(pos)).isExpandable()){
                        ((ExpandableParentItem)datas.get(pos)).setExpandable(false);
                        removeChildData(((ExpandableParentItem)datas.get(pos)).getChildDatas()) ;
                    }else{
                        ((ExpandableParentItem)datas.get(pos)).setExpandable(true);
                        insertChildData(((ExpandableParentItem)datas.get(pos)).getChildDatas(),pos+1) ;
                    }
                }else if(getItemViewType(pos)==CHLIDTYPE){
                    if(onRecyclerClickListener!=null){
                        onRecyclerClickListener.onItemClick(v,pos);
                    }
                }
            }catch (ClassCastException e){
                e.printStackTrace();
            }
        }
    } ;
    private View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Integer pos = LinearLayoutManager.class.cast(layoutManager).getPosition(v) ;
            if(getItemViewType(pos)==CHLIDTYPE){
                if(onRecyclerClickListener!=null){
                    onRecyclerClickListener.onItemLongClick(v,pos);
                }
            }
            return true;
        }
    };

    private void insertChildData(List childDatas, int startPos) {
        datas.addAll(startPos,childDatas) ;
        notifyItemRangeInserted(startPos,childDatas.size());
    }

    private void removeChildData(List childDatas) {
        int startPos = datas.indexOf(childDatas.get(0)) ;
        datas.removeAll(childDatas) ;
        notifyItemRangeRemoved(startPos,childDatas.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if(viewType==PARENTTYPE){
            holder = onCreateParentViewHolder(parent,viewType);
        }else if(viewType==CHLIDTYPE){
            holder = onCreateChildViewHolder(parent,viewType);
        }

        if(holder.itemView instanceof ScrollView){ //排除不能设置clickListener的控件

        }else{
            holder.itemView.setOnClickListener(itemCLickListener);
            holder.itemView.setOnLongClickListener(itemLongClickListener);
        }
        return holder;
    }

    protected abstract RecyclerView.ViewHolder onCreateParentViewHolder(ViewGroup parent, int viewType);

    protected abstract RecyclerView.ViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindParentViewHodler(RecyclerView.ViewHolder holder, int position,W w);

    protected abstract void onBindChildViewHodler(RecyclerView.ViewHolder holder, int position,T t);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==PARENTTYPE){
            onBindParentViewHodler(holder,position, (W) datas.get(position)) ;
        }else if(getItemViewType(position)==CHLIDTYPE){
            onBindChildViewHodler(holder,position,(T) datas.get(position)) ;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(datas.get(position) instanceof ExpandableParentItem){
            return PARENTTYPE ;
        }else if(datas.get(position) instanceof ExpandableChlidItem){
            return CHLIDTYPE ;
        }
        return getMyItemViewType(position);
    }

    protected int getMyItemViewType(int position){
        return 0 ;
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener;
    }
}

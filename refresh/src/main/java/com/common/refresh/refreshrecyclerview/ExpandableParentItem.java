package com.common.refresh.refreshrecyclerview;

import java.util.List;

/**
 * Created by zou on 2016/3/23.
 * expandable数据父基类
 */
public abstract class ExpandableParentItem<W extends ExpandableChlidItem> extends ExpandableItem {

    protected boolean isExpandable;

    protected boolean isExpandable(){
        return isExpandable ;
    }

    protected void setExpandable(boolean isExpandable){
        this.isExpandable = isExpandable ;
    }

    protected abstract List<W> getChildDatas();
}

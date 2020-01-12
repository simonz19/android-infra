package com.example.infra.ui.homelist.entity;

import java.util.List;

/**
 * Created by zou on 2016/4/13.
 */
public class HomeFeed{
    private int count;

    private List<HomeFeedDetail> data ;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setData(List<HomeFeedDetail> data){
        this.data = data;
    }
    public List<HomeFeedDetail> getData(){
        return this.data;
    }

    public List<HomeFeedDetail> getList() {
        return data;
    }
}

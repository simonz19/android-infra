package com.example.infra.ui.main;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainBindingAdapter {

    @BindingAdapter(value = "currentTabIndex")
    public static void tabpanel(TabLayout tabLayout, int index){
        tabLayout.getTabAt(index).select();
    }

    @BindingAdapter(value = "currentTabIndex")
    public static void viewPager(ViewPager viewPager, int index){
        viewPager.setCurrentItem(index);
    }
}

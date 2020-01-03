package com.example.infra.ui.home;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomeBindingAdapter {

    @BindingAdapter(value = {"homeCurrentTabIndex"})
    public static void setViewPagerIndex(ViewPager vp, int index) {
        vp.setCurrentItem(index);
    }

    @BindingAdapter(value = {"homeCurrentTabIndex"})
    public static void setViewPagerIndex(TabLayout tab, int index) {
        tab.getTabAt(index).select();
    }
}

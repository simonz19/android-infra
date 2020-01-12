package com.example.infra.ui.main;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.infra.widgets.TabLayout;

public class MainBindingAdapter {

    @BindingAdapter(value = "currentTabIndex")
    public static void tabpanel(TabLayout tabLayout, int index) {
        if (tabLayout.getSelectedPosition() == index) return;
        tabLayout.select(index);
    }

    @BindingAdapter(value = "currentTabIndex")
    public static void viewPager(ViewPager viewPager, int index) {
        if (viewPager.getCurrentItem() == index) return;
        viewPager.setCurrentItem(index);
    }
}

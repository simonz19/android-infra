package com.example.infra.common.functionalinterface;

import androidx.viewpager.widget.ViewPager;

@FunctionalInterface
public interface SimpleOnPageChangeListener extends ViewPager.OnPageChangeListener {
    @Override
    default void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    default void onPageScrollStateChanged(int state) {

    }
}

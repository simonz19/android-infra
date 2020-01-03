package com.example.infra.common.functionalinterface;

import com.google.android.material.tabs.TabLayout;

@FunctionalInterface
public interface SimpleOnTabSelectedListener extends TabLayout.OnTabSelectedListener {

    @Override
    default void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    default void onTabReselected(TabLayout.Tab tab) {

    }
}

package com.example.infra.ui.main;

import androidx.fragment.app.Fragment;

public class MainTabEntity {

    private String name;
    private int iconId;
    private Fragment fragment;

    public MainTabEntity(String name, int iconId, Fragment fragment) {
        this.name = name;
        this.iconId = iconId;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }

    public Fragment getFragment() {
        return fragment;
    }

}

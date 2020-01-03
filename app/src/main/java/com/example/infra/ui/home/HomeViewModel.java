package com.example.infra.ui.home;

import androidx.databinding.ObservableInt;

import com.example.infra.common.ui.lazyload.LazyLoadViewModel;

public class HomeViewModel extends LazyLoadViewModel {
    public ObservableInt tabIndex = new ObservableInt();
}

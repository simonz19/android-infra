package com.example.infra.common.ui.lazyload;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LazyLoadViewModel extends ViewModel {

    public LiveData<Object> data = new MutableLiveData<Object>();

}

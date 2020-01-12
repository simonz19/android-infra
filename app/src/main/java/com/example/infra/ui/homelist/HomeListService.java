package com.example.infra.ui.homelist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.infra.common.http.ObserverImpl;
import com.example.infra.common.http.RetrofitExcutor;
import com.example.infra.common.http.Retrofitter;
import com.example.infra.common.ui.lazyloadservice.LazyLoadService;
import com.example.infra.ui.homelist.entity.HomeFeed;

public class HomeListService extends LazyLoadService {

    public void loadHomeFeed(ObserverImpl<HomeFeed> observer) {
        RetrofitExcutor.get().excute(Retrofitter.getIns().get().GetHomeFeed(1), observer);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        // todo destroy requests
    }
}

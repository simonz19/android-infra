package com.example.infra.ui.homelist;

import com.example.infra.common.http.DisposableConsumerObserver;
import com.example.infra.common.http.RetrofitExcutor;
import com.example.infra.common.http.Retrofitter;
import com.example.infra.common.ui.lazyloadservice.LazyLoadService;
import com.example.infra.ui.homelist.entity.HomeFeed;

import io.reactivex.functions.Consumer;

public class HomeListService extends LazyLoadService {

    DisposableConsumerObserver<HomeFeed> homeFeedObserver;

    public void loadHomeFeed(Consumer<HomeFeed> successConsumer) {
        homeFeedObserver = setObserverConsumer(homeFeedObserver, () -> new DisposableConsumerObserver<HomeFeed>(successConsumer));
        RetrofitExcutor.get().excute(Retrofitter.getIns().get().GetHomeFeed(1), homeFeedObserver);
    }
}

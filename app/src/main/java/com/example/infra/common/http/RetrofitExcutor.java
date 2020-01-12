package com.example.infra.common.http;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RetrofitExcutor {

    private static RetrofitExcutor retrofitExcutor = new RetrofitExcutor();

    private RetrofitExcutor() {
    }

    public static RetrofitExcutor get() {
        return retrofitExcutor;
    }

    public <T> void excute(Observable<T> observable, ObserverImpl<T> observerImpl) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerImpl);
    }

    public <T> void excuteSync(Observable<T> observable, ObserverImpl<T> observerImpl) {
        observable.subscribe(observerImpl);
    }
}

package com.example.infra.common.http;


import io.reactivex.Observable;

/**
 * Created by zou on 2015/12/14.
 */
public interface IRetrofitCallServer<T> {

    Observable<T> onCallServer();

}

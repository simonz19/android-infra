package com.example.infra.common.http;

public interface IRetrofitManager {
    /**
     * Exit the login state.
     */
    void exitLogin();

    void cancelAllRequest();
}

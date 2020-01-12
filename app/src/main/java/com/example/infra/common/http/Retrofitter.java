/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.infra.common.http;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.example.infra.ManagerApplication;
import com.example.infra.common.constant.Config;
import com.example.infra.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitter implements IRetrofitManager {

    private static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;
    private static Retrofitter instance;
    private final IApiService iApiService;

    private Retrofitter() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader("Cache-Control", "public, max-age=" + 60 * 60 * 4)
                        .addHeader("appType", "ANDROID")
                        .addHeader("deviceMode", Build.BRAND)
                        .addHeader("deviceSysVersion", Build.VERSION.SDK_INT + "")
                        .addHeader("connetType", NetWorkUtils.getCurrentNetworkType(ManagerApplication.getContext()));
                Request request = builder.build();
                return chain.proceed(request);
            }
        };

        File cacheDir = new File(ManagerApplication.getContext().getCacheDir().getPath(), "manager_cache.json");
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);
        sOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.MINUTES)
                .cache(cache)
                .build();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(Config.getUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(sOkHttpClient)
                .build();
        iApiService = sRetrofit.create(IApiService.class);
    }

    public static Retrofitter getIns() {
        if (instance == null) {
            synchronized (Retrofitter.class) {
                if (instance == null) {
                    instance = new Retrofitter();
                }
            }
        }
        return instance;
    }

    public <T> T get(Class<T> tClass) {
        return sRetrofit.create(tClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> tClass) {
        T t = sRetrofit.create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, new RetrofitProxyHandler(t, this));
    }

    public IApiService get() {
        return iApiService;
    }

    public IApiService getProxy() {
        return (IApiService) Proxy.newProxyInstance(IApiService.class.getClassLoader(), new Class<?>[]{IApiService.class}, new RetrofitProxyHandler(iApiService, this));
    }

    @Override
    public void exitLogin() {
        // Cancel all the netWorkRequest
        this.cancelAllRequest();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // todo return to login page if authentication is required
            }
        });
    }

    @Override
    public void cancelAllRequest() {
        if (sOkHttpClient != null)
            sOkHttpClient.dispatcher().cancelAll();
    }
}

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

import com.example.infra.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Query;

/**
 * retry login when token timeout
 */
public class RetrofitProxyHandler implements InvocationHandler {

    private final static String TAG = "Token_Proxy";

    private final static String TOKEN = "token";

    private final static int REFRESH_TOKEN_VALID_TIME = 30;
    private static long tokenChangedTime = 0;
    private Throwable mRefreshTokenError = null;
    private boolean mIsTokenNeedRefresh;

    private Object mProxyObject;
    private IRetrofitManager mglobalManager;

    public RetrofitProxyHandler(Object proxyObject, IRetrofitManager globalManager) {
        mProxyObject = proxyObject;
        mglobalManager = globalManager;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just(null)
                .flatMap((Function<Object, Observable<?>>) o ->
                {
                    try {
                        try {
                            if (mIsTokenNeedRefresh) {
                                updateMethodToken(method, args);
                            }
                            return (Observable<?>) method.invoke(mProxyObject, args);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .retryWhen(observable ->
                        observable.flatMap((Function<Throwable, Observable<?>>) throwable ->
                        {
                            if (throwable instanceof HttpException) { //判断是否是token异常
                                Response response = ((HttpException) throwable).response();
                                try {
                                    String errorJson = response.errorBody().string();
                                    if (!StringUtils.isEmpty(errorJson)) {
                                        JSONObject jsonObj = new JSONObject(errorJson);
                                        if (jsonObj.has("resultCode")) {
                                            int resultCode = jsonObj.getInt("resultCode");
                                            if (resultCode == 13) { //
                                                return refreshTokenWhenTokenInvalid();
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            return Observable.error(throwable);
                        }));
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (RetrofitProxyHandler.class) {
            // Have refreshed the token successfully in the valid time.
            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                mIsTokenNeedRefresh = true;
                return Observable.just(true);
            } else {
//                // call the refresh token api.
//                Retrofitter.getIns().get(IApiService.class).staffPhoneLogin(RememberMe.get().getUsername(), RememberMe.get().getPasswd()).subscribe(new Subscriber<ManagerUser>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mRefreshTokenError = e;
//                    }
//
//                    @Override
//                    public void onNext(ManagerUser model) {
//                        if (model != null) {
//                            mIsTokenNeedRefresh = true;
//                            tokenChangedTime = new Date().getTime();
//                            AuthenticationUser user = new AuthenticationUser(RememberMe.get().getUsername(), RememberMe.get().getPasswd());
//                            user.setPrincipal(model);
//                            AuthenticateManager.get().populateUserInfo(user);
//                            Log.d(TAG, "Refresh token success, time = " + tokenChangedTime);
//                        }
//                    }
//                });
                if (mRefreshTokenError != null) {
                    mglobalManager.exitLogin();
                    return Observable.error(mRefreshTokenError);
                } else {
                    return Observable.just(true);
                }
            }
        }
    }

    /**
     * Update the token of the args in the method.
     * <p>
     * PS： 因为这里使用的是 GET 请求，所以这里就需要对 Query 的参数名称为 token 的方法。
     * 若是 POST 请求，或者使用 Body ，自行替换。因为 参数数组已经知道，进行遍历找到相应的值，进行替换即可（更新为新的 token 值）。
     */
    private void updateMethodToken(Method method, Object[] args) {
        if (mIsTokenNeedRefresh) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Query) {
                            if (TOKEN.equals(((Query) annotation).value())) {
//                                args[i] = AppHelper.getIUser().getToken();
                                break;
                            }
                        } else if (annotation instanceof Field) {
                            if (TOKEN.equals(((Field) annotation).value())) {
//                                args[i] = AppHelper.getIUser().getToken();
                                break;
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }
}

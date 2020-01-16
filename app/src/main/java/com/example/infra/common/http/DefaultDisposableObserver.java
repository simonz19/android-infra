package com.example.infra.common.http;

import com.example.infra.common.entity.CommonResponse;
import com.example.infra.util.StringUtils;
import com.example.infra.util.ToastUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class DefaultDisposableObserver<T> extends DisposableObserver<T> {

    @Override
    public final void onNext(T t) {
        String errorMsg = "未知错误!";
        if (t != null) {
            if (t instanceof CommonResponse && !((CommonResponse) t).isSuccess()) {
                if (!StringUtils.isEmpty(((CommonResponse) t).getResultMessage())) {
                    errorMsg = ((CommonResponse) t).getResultMessage();
                }
                ToastUtil.showMessage(errorMsg);
                onFailed(RetrofitErrorType.UNKNOW, errorMsg);
            } else {
                onSuccess(t);
            }
        } else {
            ToastUtil.showMessage(errorMsg);
            onFailed(RetrofitErrorType.UNKNOW, errorMsg);
        }
    }

    @Override
    public final void onError(Throwable e) {
        String errorMsg;
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不给力,请稍后再试!";
            ToastUtil.showMessage(errorMsg);
            onFailed(RetrofitErrorType.NETWORKERROR, errorMsg);
        } else if (e instanceof HttpException) {
            errorMsg = "服务异常,请联系管理员";
            try {
                String string = ((HttpException) e).response().errorBody().string();
                if (string.contains("resultMessage")) {
                    JSONObject jsonObject = new JSONObject(string);
                    errorMsg = jsonObject.getString("resultMessage");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            ToastUtil.showMessage(errorMsg);
            onFailed(RetrofitErrorType.SERVERERROR, errorMsg);
        } else {
            errorMsg = "后台链接异常,请联系管理员";
            ToastUtil.showMessage(errorMsg);
            onFailed(RetrofitErrorType.UNKNOW, errorMsg);
            if (!StringUtils.isEmpty(e.getMessage())) {
                errorMsg = e.getMessage();
            }
            Logger.e(errorMsg);
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);

    protected void onFailed(RetrofitErrorType errorType, String errorMsg) {
//        ToastUtil.showMessage(errorMsg);
    }
}

package com.example.infra.common.http;


import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DisposableConsumerObserver<T> extends DefaultDisposableObserver<T> {

    private Consumer<T> successConsumer;
    private Consumer<RetrofitErrorType> failedConsumer;
    private Action completeAction;

    public DisposableConsumerObserver(Consumer<T> successConsumer) {
        this.successConsumer = successConsumer;
    }

    public DisposableConsumerObserver(Consumer<T> successConsumer, Consumer<RetrofitErrorType> failedConsumer) {
        this.successConsumer = successConsumer;
        this.failedConsumer = failedConsumer;
    }

    public DisposableConsumerObserver(Consumer<T> successConsumer, Consumer<RetrofitErrorType> failedConsumer, Action completeAction) {
        this.successConsumer = successConsumer;
        this.failedConsumer = failedConsumer;
        this.completeAction = completeAction;
    }

    @Override
    protected void onSuccess(T t) {
        if(successConsumer != null) {
            try {
                successConsumer.accept(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onFailed(RetrofitErrorType errorType, String errorMsg) {
        super.onFailed(errorType, errorMsg);
        if(failedConsumer != null){
            try {
                failedConsumer.accept(errorType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if(completeAction != null){
            try {
                completeAction.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

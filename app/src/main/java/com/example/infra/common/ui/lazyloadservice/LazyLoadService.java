package com.example.infra.common.ui.lazyloadservice;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.infra.common.http.DisposableConsumerObserver;

import io.reactivex.disposables.CompositeDisposable;

public abstract class LazyLoadService implements DefaultLifecycleObserver {

    public interface OnCreateObserverConsumer<T> {
        DisposableConsumerObserver<T> onCreate();
    }

    protected CompositeDisposable disposables = new CompositeDisposable();

    /**
     * if exist then dispose, then add in to compositeDisposable
     *
     * @param observerConsumer
     * @param onCreateObserverConsumer
     * @param <T>
     */
    protected <T> DisposableConsumerObserver<T> setObserverConsumer(DisposableConsumerObserver<T> observerConsumer,
                                           @NonNull OnCreateObserverConsumer<T> onCreateObserverConsumer) {
        if (observerConsumer != null && !observerConsumer.isDisposed())
            disposables.remove(observerConsumer);
        observerConsumer = onCreateObserverConsumer.onCreate();
        disposables.add(observerConsumer);
        return observerConsumer;
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (!disposables.isDisposed()) disposables.clear();
    }
}

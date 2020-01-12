package com.example.infra.common.ui.lazyloadservice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.DefaultLifecycleObserver;

import com.example.infra.common.ui.lazyload.LazyLoadFragment;
import com.example.infra.common.ui.lazyload.LazyLoadViewModel;

/**
 * extend lifeCycle as: onCreateView ( => onGetResourceId => onViewBound?) => onActivityCreated ( => onCreateService)
 *
 * @param <T>
 * @param <W>
 * @param <Y>
 */
public abstract class LazyLoadServiceFragment<T extends ViewDataBinding, W extends LazyLoadViewModel, Y extends DefaultLifecycleObserver>
        extends LazyLoadFragment<T, W> {

    protected Y mService;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mService = onCreateService();
        getLifecycle().addObserver(mService);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mService);
    }

    /**
     * create service for this fragment
     *
     * @return LifeCycleObserver
     */
    protected abstract @NonNull
    Y onCreateService();
}

package com.example.infra.common.ui.lazyloadservice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.DefaultLifecycleObserver;

import com.example.infra.common.ui.lazyload.LazyLoadFragment;
import com.example.infra.common.ui.lazyload.LazyLoadViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * extend lifeCycle as: onCreateView ( => onGetResourceId => onViewBound?) => onActivityCreated ( => onCreateService)
 *
 * @param <T>
 * @param <W>
 * @param <Y>
 */
public abstract class LazyLoadServiceFragment<T extends ViewDataBinding, W extends LazyLoadViewModel, Y extends LazyLoadService>
        extends LazyLoadFragment<T, W> {

    protected Y mService;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mService = onCreateService();
        if (mService == null) {
            try {
                Type type = getClass().getGenericSuperclass();
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<Y> serviceClass = (Class<Y>) parameterizedType.getActualTypeArguments()[2];
                mService = serviceClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }

        if (mService == null) throw new NullPointerException("failed to create LazyLoadService, " +
                "pls at least provide a non parameter constructor or implement onCreateService function in fragment");
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
    protected Y onCreateService() {
        return null;
    }

}

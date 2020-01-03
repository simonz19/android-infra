package com.example.infra.common.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<T extends ViewDataBinding, W extends ViewModel> extends Fragment {

    protected T mBind;
    protected W mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {

                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<W> vmClass = (Class<W>) parameterizedType.getActualTypeArguments()[1];
                mViewModel = ViewModelProviders.of(this).get(vmClass);
                mBind = DataBindingUtil.<T>inflate(inflater, onGetResourceId(), container, false);
                return mBind.getRoot();
            } finally {
                onViewBound();
            }
        } else {
            return inflater.inflate(onGetResourceId(), container, false);
        }
    }

    /**
     * extend the default lifecycle with onViewBound when view is bound into ViewDataBinding
     * you can do the view initiation within this function
     */
    protected void onViewBound() {
    }

    /**
     * the layout resource id for fragment onCreateView lifecycle
     *
     * @return the id of layout resource
     */
    protected abstract int onGetResourceId();
}

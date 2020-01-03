package com.example.infra.ui.homelist;

import android.os.Bundle;
import android.util.Log;

import com.example.infra.R;
import com.example.infra.common.ui.BaseFragment;
import com.example.infra.common.ui.lazyload.LazyLoadFragment;
import com.example.infra.databinding.HomeListFragmentBinding;

public class HomeListFragment extends LazyLoadFragment<HomeListFragmentBinding, HomeListViewModel> {

    public static HomeListFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("index", index);
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart==> " + getArguments().getInt("index"));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume==> " + getArguments().getInt("index"));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause==>" + getArguments().getInt("index"));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop==> " + getArguments().getInt("index"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy==> " + getArguments().getInt("index"));
    }

    @Override
    protected int onGetResourceId() {
        return R.layout.home_list_fragment;
    }


    @Override
    protected boolean onLazyLoad() {
        showContent();
        return super.onLazyLoad();
    }
}

package com.example.infra.ui.homelist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infra.R;
import com.example.infra.common.adapter.SimpleBaseBindingAdapter;
import com.example.infra.common.http.ObserverImpl;
import com.example.infra.common.ui.lazyloadservice.LazyLoadServiceFragment;
import com.example.infra.databinding.HomeListFragmentBinding;
import com.example.infra.databinding.HomeListItemBinding;
import com.example.infra.ui.homelist.entity.HomeFeed;
import com.example.infra.ui.homelist.entity.HomeFeedDetail;

public class HomeListFragment extends LazyLoadServiceFragment<HomeListFragmentBinding, HomeListViewModel, HomeListService> {

    SimpleBaseBindingAdapter<HomeFeedDetail, HomeListItemBinding> adapter;

    public static HomeListFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("index", index);
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onViewBound() {
        adapter = new SimpleBaseBindingAdapter<HomeFeedDetail, HomeListItemBinding>(getContext(), R.layout.home_list_item) {

            @Override
            protected void onSimpleBindItem(HomeListItemBinding binding, HomeFeedDetail item, RecyclerView.ViewHolder holder) {
                String url = item.getImg1() == null ? item.getPic1Url() : item.getImg1();
                Glide.with(getContext()).load(url).into(binding.imageItem);
            }
        };
        mBind.homeRecycleView.setAdapter(adapter);
    }

    @Override
    protected int onGetResourceId() {
        return R.layout.home_list_fragment;
    }

    @NonNull
    @Override
    protected HomeListService onCreateService() {
        return new HomeListService();
    }

    @Override
    protected boolean onLazyLoad() {
        mService.loadHomeFeed(new ObserverImpl<HomeFeed>() {
            @Override
            protected void onSuccess(HomeFeed homeFeed) {
                adapter.setList(homeFeed.getData());
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
        return super.onLazyLoad();
    }
}

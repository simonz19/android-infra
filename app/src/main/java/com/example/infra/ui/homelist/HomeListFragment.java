package com.example.infra.ui.homelist;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.common.refresh.refreshrecyclerview.RefreshRecyclerView;
import com.example.infra.R;
import com.example.infra.common.adapter.SimpleBaseBindingAdapter;
import com.example.infra.common.http.Retrofitter;
import com.example.infra.common.ui.lazyloadservice.LazyLoadServiceFragment;
import com.example.infra.databinding.HomeListFragmentBinding;
import com.example.infra.databinding.HomeListItemBinding;
import com.example.infra.ui.homelist.entity.HomeFeed;
import com.example.infra.ui.homelist.entity.HomeFeedDetail;
import com.example.infra.util.LoadingHandler;

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
            protected void onBindItem(HomeListItemBinding binding, HomeFeedDetail item, RecyclerView.ViewHolder holder) {
                String url = item.getImg1() == null ? item.getPic1Url() : item.getImg1();
                Glide.with(getContext()).load(url).into(binding.imageItem);
            }
        };
        mBind.homeRecycleView.setPageSize(10);
        mBind.homeRecycleView.setMode(RefreshRecyclerView.Mode.ALL);
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

        final LoadingHandler.PageNumHolder pageNumHolder = new LoadingHandler.PageNumHolder(1);
        LoadingHandler loadingHandler = new LoadingHandler.Builder<HomeFeed>()
                .setListView(mBind.homeRecycleView)
                .setIProgressListener(this)
                .setOnRefreshListener(new LoadingHandler.OnRefreshListener<HomeFeed>() {
                    @Override
                    public void onRefreshComplete(HomeFeed homeFeed) {
                        adapter.setData(homeFeed.getList());
                    }

                    @Override
                    public void onLoadComplete(HomeFeed homeFeed) {
                        adapter.addData(homeFeed.getList());
                    }
                })
                .setPageNumHolder(pageNumHolder)
                .setIRetrofitCallServer(() -> Retrofitter.getIns().get().GetHomeFeed(pageNumHolder.pageNum))
                .build();

        loadingHandler.loadData();

//        mService.loadHomeFeed(homeFeed -> {
//            adapter.setList(homeFeed.getData());
//            adapter.notifyDataSetChanged();
//            showContent();
//        });
        return super.onLazyLoad();
    }
}

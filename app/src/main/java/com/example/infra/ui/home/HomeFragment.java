package com.example.infra.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.infra.R;
import com.example.infra.common.functionalinterface.SimpleOnPageChangeListener;
import com.example.infra.common.functionalinterface.SimpleOnTabSelectedListener;
import com.example.infra.common.ui.lazyload.LazyLoadFragment;
import com.example.infra.databinding.HomeFragmentBinding;
import com.example.infra.ui.homelist.HomeListFragment;

public class HomeFragment extends LazyLoadFragment<HomeFragmentBinding, HomeViewModel> {

    private HomeFragment() {
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGetResourceId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void onViewBound() {
        mBind.setHomeVM(mViewModel);
        mBind.homeTabLayout.addOnTabSelectedListener((SimpleOnTabSelectedListener) tab -> mViewModel.tabIndex.set(tab.getPosition()));
        mBind.homeFragmentViewPager.addOnPageChangeListener((SimpleOnPageChangeListener) position -> mViewModel.tabIndex.set(position));
        mBind.homeFragmentViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        mBind.homeFragmentViewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected boolean onLazyLoad() {
        showContent();
        return super.onLazyLoad();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments = {
                HomeListFragment.newInstance(1),
                HomeListFragment.newInstance(2),
                HomeListFragment.newInstance(3),
                HomeListFragment.newInstance(4)
        };

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}

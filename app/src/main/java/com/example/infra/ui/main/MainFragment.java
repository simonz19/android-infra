package com.example.infra.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.annotation.NonNull;

import com.example.infra.common.ui.BaseFragment;
import com.example.infra.R;
import com.example.infra.common.functionalinterface.SimpleOnPageChangeListener;
import com.example.infra.common.functionalinterface.SimpleOnTabSelectedListener;
import com.example.infra.databinding.MainFragmentBinding;
import com.example.infra.ui.home.HomeFragment;
import com.example.infra.ui.me.MeFragment;

public class MainFragment extends BaseFragment<MainFragmentBinding, MainViewModel> {

    @Override
    public int onGetResourceId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void onViewBound() {
        mBind.setMainVM(mViewModel);
        mBind.mainTabLayout.addOnTabSelectedListener((SimpleOnTabSelectedListener) tab -> mViewModel.tabIndex.set(tab.getPosition()));
        mBind.mainFragmentViewPager.addOnPageChangeListener((SimpleOnPageChangeListener) position -> mViewModel.tabIndex.set(position));
        mBind.mainFragmentViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments = {HomeFragment.newInstance(), MeFragment.newInstance(R.color.colorPrimaryDark)};

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

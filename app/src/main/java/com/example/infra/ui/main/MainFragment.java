package com.example.infra.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.annotation.NonNull;

import com.example.infra.common.ui.BaseFragment;
import com.example.infra.R;
import com.example.infra.common.functionalinterface.SimpleOnPageChangeListener;
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
        MainTabEntity[] mainTabEntities = new MainTabEntity[]{
                new MainTabEntity("首页", R.drawable.ic_main_tab_home, HomeFragment.newInstance()),
                new MainTabEntity("首页", R.drawable.ic_main_tab_user, MeFragment.newInstance(R.color.colorPrimaryDark)),
        };
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), mainTabEntities);
        mBind.mainFragmentViewPager.addOnPageChangeListener((SimpleOnPageChangeListener) position -> mViewModel.tabIndex.set(position));
        mBind.mainFragmentViewPager.setAdapter(pagerAdapter);
        mBind.mainFragmentViewPager.setOffscreenPageLimit(pagerAdapter.getCount() - 1);
        mBind.mainTabLayout.setOnSelcetedListener(index -> mViewModel.tabIndex.set(index));
        for (MainTabEntity entity : mainTabEntities) {
            mBind.mainTabLayout.addTab(inflateMenuItem(entity));
        }
    }

    /**
     * inflate the menu item
     *
     * @param entity
     * @return
     */
    private View inflateMenuItem(MainTabEntity entity) {
        View childView = View.inflate(getContext(), R.layout.main_tab_layout, null);
        ImageView.class.cast(childView.findViewById(R.id.btnIcon)).setImageResource(entity.getIconId());
        TextView.class.cast(childView.findViewById(R.id.btnLabel)).setText(entity.getName());
        return childView;
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private MainTabEntity[] mainTabEntities;

        public PagerAdapter(@NonNull FragmentManager fm, MainTabEntity[] mainTabEntities) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.mainTabEntities = mainTabEntities;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mainTabEntities[position].getFragment();
        }

        @Override
        public int getCount() {
            return mainTabEntities.length;
        }
    }
}

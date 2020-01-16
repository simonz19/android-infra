package com.example.infra.common.ui.lazyload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentPagerAdapter;

import com.common.progress.ProgressView;
import com.example.infra.R;
import com.example.infra.common.ui.BaseFragment;

import java.util.Collections;

/**
 * extends lifecycle with {@link LazyLoadFragment#onLazyLoad()} and integrate with
 * a progress loading view before loading complete.
 * <p>
 * when used in ViewPager, please use with behavior {@link FragmentPagerAdapter#BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT}
 *
 * @param <T>
 * @param <W>
 */
public abstract class LazyLoadFragment<T extends ViewDataBinding, W extends LazyLoadViewModel>
        extends BaseFragment<T, W> implements IProgressListener {

    private boolean isLoaded;
    private ProgressView progressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        progressView = (ProgressView) inflater.inflate(R.layout.lazy_load_fragment, null, false);
        if (view != null) {
            progressView.addView(view, 0, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        showLoading();
        return progressView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            isLoaded = onLazyLoad();
        }
    }

    @Override
    public void showLoading() {
        if (!progressView.isLoading())
            progressView.showLoading(getString(R.string.loading_press));
    }

    @Override
    public void showLoading2() {
        if (!progressView.isLoading2())
            progressView.showLoading2(getString(R.string.loading_press));
    }

    @Override
    public void showSubmit() {
        if (!progressView.isSubmit())
            progressView.showSubmit(getString(R.string.submit_press));
    }

    @Override
    public void showContent() {
        if (!progressView.isContent())
            progressView.showContent();
    }

    @Override
    public void showEmpty(View.OnClickListener onClickListener) {
        if (!progressView.isEmpty())
            progressView.showEmpty(getResources().getDrawable(R.drawable.global_loading_null), getString(R.string.data_null),
                    getString(R.string.load_retry), onClickListener, Collections.EMPTY_LIST);
    }

    @Override
    public void showError(View.OnClickListener onClickListener) {
        if (!progressView.isError())
            progressView.showError(getResources().getDrawable(R.drawable.global_loading_retry),
                    getString(R.string.error_happened), getString(R.string.load_retry), onClickListener);
    }

    /**
     * extended lifecycle function which will be invoked when current fragment is resumed
     *
     * @return return false if onLazyLoad is needed to be invoked next time when resumed
     */
    protected boolean onLazyLoad() {
        return true;
    }
}

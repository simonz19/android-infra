package com.example.infra.common.ui.lazyload;

import android.graphics.drawable.Drawable;
import android.view.View;
import java.util.List;

public interface ILazyLoad {
    void showLoading(String text);

    void showSubmit(String text);

    void showContent();

    void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, List<Integer> skipIds);

    void showError(Drawable emptyImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, View.OnClickListener onClickListener);
}

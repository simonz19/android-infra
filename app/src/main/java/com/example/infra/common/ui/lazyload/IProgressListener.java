package com.example.infra.common.ui.lazyload;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;

public interface IProgressListener {
    default void showLoading(String text) {
    }

    default void showLoading2(String submitTxt) {

    }

    default void showSubmit(String text) {
    }

    default void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, View.OnClickListener onClickListener, List<Integer> skipIds) {

    }

    default void showError(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, View.OnClickListener onClickListener) {

    }

    void showLoading();

    void showLoading2();

    void showContent();

    void showSubmit();

    void showEmpty(View.OnClickListener onClickListener);

    void showError(View.OnClickListener onClickListener);
}

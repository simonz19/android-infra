package com.common.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class ProgressView extends RelativeLayout {

    private static final String TAG_LOADING = "ProgressView.TAG_LOADING";
    private static final String TAG_EMPTY = "ProgressView.TAG_EMPTY";
    private static final String TAG_ERROR = "ProgressView.TAG_ERROR";

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;

    List<View> contentViews = new ArrayList<>();

    ProgressDialog progressDialog;

    RelativeLayout emptyStateRelativeLayout;
    ImageView emptyStateImageView;
    TextView emptyStateTitleTextView;
    TextView emptyStateContentTextView;

    RelativeLayout errorStateRelativeLayout;
    ImageView errorStateImageView;
    TextView errorStateTitleTextView;
    TextView errorStateContentTextView;
    Button errorStateButton;

    int loadingStateProgressBarWidth;
    int loadingStateProgressBarHeight;
    int loadingStateBackgroundColor;

    int emptyStateImageWidth;
    int emptyStateImageHeight;
    int emptyStateTitleTextSize;
    int emptyStateContentTextSize;
    int emptyStateTitleTextColor;
    int emptyStateContentTextColor;
    int emptyStateBackgroundColor;

    int errorStateImageWidth;
    int errorStateImageHeight;
    int errorStateTitleTextSize;
    int errorStateContentTextSize;
    int errorStateTitleTextColor;
    int errorStateContentTextColor;
    int errorStateButtonTextColor;
    int errorStateBackgroundColor;

    private State state = State.CONTENT;
    private RelativeLayout loadingStateRelativeLayout;
    private TextView tv_loading;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressView);

        //Loading state attrs
        loadingStateProgressBarWidth =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressLoadingStateProgressBarWidth, 108);

        loadingStateProgressBarHeight =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressLoadingStateProgressBarHeight, 108);

        loadingStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressView_progressLoadingStateBackgroundColor, Color.TRANSPARENT);

        //Empty state attrs
        emptyStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressEmptyStateImageWidth, 308);

        emptyStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressEmptyStateImageHeight, 308);

        emptyStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressEmptyStateTitleTextSize, 15);

        emptyStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressEmptyStateContentTextSize, 14);

        emptyStateTitleTextColor =
                typedArray.getColor(R.styleable.ProgressView_progressEmptyStateTitleTextColor, Color.BLACK);

        emptyStateContentTextColor =
                typedArray.getColor(R.styleable.ProgressView_progressEmptyStateContentTextColor, Color.BLACK);

        emptyStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressView_progressEmptyStateBackgroundColor, Color.TRANSPARENT);

        //Error state attrs
        errorStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressErrorStateImageWidth, 308);

        errorStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressErrorStateImageHeight, 308);

        errorStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressErrorStateTitleTextSize, 15);

        errorStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressErrorStateContentTextSize, 14);

        errorStateTitleTextColor =
                typedArray.getColor(R.styleable.ProgressView_progressErrorStateTitleTextColor, Color.BLACK);

        errorStateContentTextColor =
                typedArray.getColor(R.styleable.ProgressView_progressErrorStateContentTextColor, Color.BLACK);

        errorStateButtonTextColor =
                typedArray.getColor(R.styleable.ProgressView_progressErrorStateButtonTextColor, Color.BLACK);

        errorStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressView_progressErrorStateBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) && !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            contentViews.add(child);
        }
    }

    /**
     * Hide all other states and show content
     */
    public void showContent() {
        switchState(State.CONTENT, null, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide all other states and show content
     *
     * @param skipIds Ids of views not to show
     */
    public void showContent(List<Integer> skipIds) {
        switchState(State.CONTENT, null, null, null, null, null, skipIds);
    }

    /**
     * Hide content and show the progress bar
     */
    public void showLoading(String loadingText) {
        switchState(State.LOADING, null, loadingText, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar
     *
     * @param skipIds Ids of views to not hide
     */
    public void showLoading(String loadingText, List<Integer> skipIds) {
        switchState(State.LOADING, null, loadingText, null, null, null, skipIds);
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageDrawable Drawable to show
     * @param emptyTextTitle     Title of the empty view to show
     * @param emptyTextContent   Content of the empty view to show
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent) {
        switchState(State.EMPTY, emptyImageDrawable, emptyTextTitle, emptyTextContent, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageDrawable Drawable to show
     * @param emptyTextTitle     Title of the empty view to show
     * @param emptyTextContent   Content of the empty view to show
     * @param skipIds            Ids of views to not hide
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, List<Integer> skipIds) {
        switchState(State.EMPTY, emptyImageDrawable, emptyTextTitle, emptyTextContent, null, null, skipIds);
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to show
     * @param errorTextTitle     Title of the error view to show
     * @param errorTextContent   Content of the error view to show
     * @param errorButtonText    Text on the error view button to show
     * @param onClickListener    Listener of the error view button
     */
    public void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener) {
        switchState(State.ERROR, errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to show
     * @param errorTextTitle     Title of the error view to show
     * @param errorTextContent   Content of the error view to show
     * @param errorButtonText    Text on the error view button to show
     * @param onClickListener    Listener of the error view button
     * @param skipIds            Ids of views to not hide
     */
    public void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        switchState(State.ERROR, errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener, skipIds);
    }

    /**
     * show submitdialog
     * @param emptyTextTitle
     */
    public void showSubmit(String emptyTextTitle) {
        switchState(State.SUBMIT, null, emptyTextTitle, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Get which state is set
     *
     * @return State
     */
    public State getState() {
        return state;
    }

    /**
     * Check if content is shown
     *
     * @return boolean
     */
    public boolean isContent() {
        return state == State.CONTENT;
    }

    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isLoading() {
        return state == State.LOADING;
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return state == State.EMPTY;
    }

    /**
     * Check if submit state is shown
     *
     * @return boolean
     */
    public boolean isSubmit(){
        return state == State.SUBMIT ;
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isError() {
        return state == State.ERROR;
    }

    private void switchState(State state, Drawable drawable, String errorText, String errorTextContent,
                             String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                hideLoadingView();
                hideEmptyView();
                hideErrorView();
                hideSumitView() ;
                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();
                hideSumitView() ;
                setLoadingView(errorText);
                setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();
                hideSumitView() ;
                setEmptyView();
                emptyStateImageView.setImageDrawable(drawable);
                emptyStateTitleTextView.setText(errorText);
                emptyStateContentTextView.setText(errorTextContent);
                setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideLoadingView();
                hideEmptyView();
                hideSumitView() ;
                setErrorView();
                errorStateImageView.setImageDrawable(drawable);
                errorStateTitleTextView.setText(errorText);
                errorStateContentTextView.setText(errorTextContent);
                errorStateButton.setText(errorButtonText);
                errorStateButton.setOnClickListener(onClickListener);
                setContentVisibility(false, skipIds);
                break;
            case SUBMIT:
                setSubmitView(errorText);
                break ;
        }
    }

    private void setSubmitView(String errorText) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext(), errorText);
        else progressDialog.setPressText(errorText);
        progressDialog.show();
    }

    private void setLoadingView(String loadingText) {
        view = inflater.inflate(R.layout.progress_loading_view, null);
        loadingStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.loadingStateRelativeLayout);
        loadingStateRelativeLayout.setTag(TAG_LOADING);
        tv_loading = (TextView) loadingStateRelativeLayout.findViewById(R.id.loading_text);
        if(loadingText!=null&&!loadingText.equals("")){
            tv_loading.setText(loadingText);
        }

        //Set background color if not TRANSPARENT
        if (loadingStateBackgroundColor != Color.TRANSPARENT) {
            loadingStateRelativeLayout.setBackgroundColor(loadingStateBackgroundColor);
        }

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(loadingStateRelativeLayout, layoutParams);
    }

    private void setEmptyView() {
        view = inflater.inflate(R.layout.progress_empty_view, null);
        emptyStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.emptyStateRelativeLayout);
        emptyStateRelativeLayout.setTag(TAG_EMPTY);

        emptyStateImageView = (ImageView) view.findViewById(R.id.emptyStateImageView);
        emptyStateTitleTextView = (TextView) view.findViewById(R.id.emptyStateTitleTextView);
        emptyStateContentTextView = (TextView) view.findViewById(R.id.emptyStateContentTextView);

        //Set empty state image width and height
        emptyStateImageView.getLayoutParams().width = emptyStateImageWidth;
        emptyStateImageView.getLayoutParams().height = emptyStateImageHeight;
        emptyStateImageView.requestLayout();

        emptyStateTitleTextView.setTextSize(emptyStateTitleTextSize);
        emptyStateContentTextView.setTextSize(emptyStateContentTextSize);
        emptyStateTitleTextView.setTextColor(emptyStateTitleTextColor);
        emptyStateContentTextView.setTextColor(emptyStateContentTextColor);

        //Set background color if not TRANSPARENT
        if (emptyStateBackgroundColor != Color.TRANSPARENT) {
            emptyStateRelativeLayout.setBackgroundColor(emptyStateBackgroundColor);
        }

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(emptyStateRelativeLayout, layoutParams);
    }

    private void setErrorView() {
        view = inflater.inflate(R.layout.progress_error_view, null);
        errorStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.errorStateRelativeLayout);
        errorStateRelativeLayout.setTag(TAG_ERROR);

        errorStateImageView = (ImageView) view.findViewById(R.id.errorStateImageView);
        errorStateTitleTextView = (TextView) view.findViewById(R.id.errorStateTitleTextView);
        errorStateContentTextView = (TextView) view.findViewById(R.id.errorStateContentTextView);
        errorStateButton = (Button) view.findViewById(R.id.errorStateButton);

        //Set error state image width and height
        errorStateImageView.getLayoutParams().width = errorStateImageWidth;
        errorStateImageView.getLayoutParams().height = errorStateImageHeight;
        errorStateImageView.requestLayout();

        errorStateTitleTextView.setTextSize(errorStateTitleTextSize);
        errorStateContentTextView.setTextSize(errorStateContentTextSize);
        errorStateTitleTextView.setTextColor(errorStateTitleTextColor);
        errorStateContentTextView.setTextColor(errorStateContentTextColor);
        errorStateButton.setTextColor(errorStateButtonTextColor);

        //Set background color if not TRANSPARENT
        if (errorStateBackgroundColor != Color.TRANSPARENT) {
            errorStateRelativeLayout.setBackgroundColor(errorStateBackgroundColor);
        }

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(errorStateRelativeLayout, layoutParams);
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void hideLoadingView() {
        if(loadingStateRelativeLayout!=null){
            loadingStateRelativeLayout.setVisibility(View.GONE);
        }
    }

    private void hideSumitView(){
        if (progressDialog != null) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    private void hideEmptyView() {
        if (emptyStateRelativeLayout != null) {
            emptyStateRelativeLayout.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorStateRelativeLayout != null) {
            errorStateRelativeLayout.setVisibility(GONE);
        }
    }

    public static enum State {
        CONTENT, LOADING, EMPTY, ERROR, SUBMIT
    }
}
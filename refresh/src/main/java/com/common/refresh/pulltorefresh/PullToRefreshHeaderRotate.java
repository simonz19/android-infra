package com.common.refresh.pulltorefresh;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.refresh.R;


/**
 * Created by zou on 2016/3/21.
 */
public class PullToRefreshHeaderRotate extends PullToRefreshHeaderBase {

    private TextView tv_header;
    private ImageView iv_header;
    private Matrix headerMaxtrix;
    private int mRotationPivotX;
    private int mRotationPivotY;
    private RotateAnimation mRotateAnimation;
    static final int ROTATION_ANIMATION_DURATION = 1200;
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    private String pullTitle ;

    public PullToRefreshHeaderRotate(Context context, String pullTitle) {
        super(context);
        if(pullTitle==null){
            this.pullTitle = "" ;
        } else{
            this.pullTitle = pullTitle ;
        }
        initView(context) ;
    }

    public PullToRefreshHeaderRotate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context) ;
    }

    @Override
    public void onPullToRefresh() {
        tv_header.setText(String.format(getResources().getString(R.string.pullToRefresh),pullTitle));
    }

    @Override
    public void onRefreshing() {
        iv_header.startAnimation(mRotateAnimation);
        tv_header.setText(String.format(getResources().getString(R.string.Refreshing),pullTitle));
    }

    @Override
    public void onReleaseToRefresh() {
        tv_header.setText(String.format(getResources().getString(R.string.releaseToRefresh),pullTitle));
    }

    public void initView(Context context){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) pxTodp(context, 50f));
        setLayoutParams(params);
        setBackgroundColor(0xFFf5f5f5);
        tv_header = new TextView(context);
        RelativeLayout.LayoutParams paramsTv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsTv.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_header.setLayoutParams(paramsTv);
        tv_header.setTextSize(15);
        addView(tv_header);

        iv_header = new ImageView(getContext());
        RelativeLayout.LayoutParams paramsIv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsIv.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
        paramsIv.leftMargin = (int) pxTodp(getContext(),24);
        iv_header.setLayoutParams(paramsIv);
        iv_header.setImageResource(R.drawable.default_ptr_rotate);
        onLoadingDrawableSet(iv_header.getDrawable()) ;
        headerMaxtrix = new Matrix();
        iv_header.setScaleType(ImageView.ScaleType.MATRIX);
        iv_header.setImageMatrix(headerMaxtrix);
        addView(iv_header);

        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    public float pxTodp(Context context, float px) {
        return context.getResources().getDisplayMetrics().density * px + 0.5f;
    }

    /**
     * 图片旋转中心
     * @param imageDrawable
     */
    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
            mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
        }
    }

    /**
     * 重置matrix
     */
    private void resetImageRotation() {
        if (null != headerMaxtrix) {
            headerMaxtrix.reset();
            iv_header.setImageMatrix(headerMaxtrix);
        }
    }

    /**
     * 下拉时转动头部图片
     * @param scaleOfLayout
     */
    @Override
    public void onPullAnim(float scaleOfLayout) {
        float angle;
        angle = scaleOfLayout * 90f;
        headerMaxtrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
        iv_header.setImageMatrix(headerMaxtrix);
    }

    @Override
    public void onRefreshComplete() {
        iv_header.clearAnimation();
        resetImageRotation() ;
    }
}

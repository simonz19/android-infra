package com.common.refresh.pulltorefresh;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Scroller;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zou on 2015/12/31.
 * 支持的内容视图包括listview ,recyclerview , gridview
 */
public class PullToRefreshContainer extends ViewGroup {

    public static final int DOREFRESH = 0;
    public static final int DONOTREFRESH = 1;
    private int refreshMode = -1;
    private PullToRefreshHeaderBase headerView;
    private View contentView;
    private IRefreshing iRefreshing;
    private final int MAXSCROLL = getContext().getResources().getDisplayMetrics().heightPixels / 3;
    private int dy;
    private float mInitialMotionX;
    private boolean isRefreshing;

    public PullToRefreshContainer(Context context, PullToRefreshHeaderBase headerView) {
        super(context);
        this.headerView = headerView;
        init(context);
    }

    public PullToRefreshContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
    }

    /**
     * 设置内容视图
     *
     * @param view
     */
    public void setContentView(View view) {
        contentView = view;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (headerView != null) {
            int titleHeightMeasureSpec = MeasureSpec.makeMeasureSpec(headerView.getLayoutParams().height, MeasureSpec.EXACTLY);
            headerView.measure(widthMeasureSpec, titleHeightMeasureSpec);
        } else {
            setHeaderLayout(new PullToRefreshHeaderRotate(getContext(), ""));
        }

        if (contentView != null) {
            contentView.measure(widthMeasureSpec, heightMeasureSpec);
        } else {
            if (getChildCount() > 2) {
                throw new IllegalStateException("only two child is enabled!");
            } else if (getChildAt(0) == headerView) {
                contentView = getChildAt(1);
            } else if (getChildAt(1) == headerView) {
                contentView = getChildAt(0);
            }
            contentView.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (headerView != null) {
            headerView.layout(l, t - headerView.getMeasuredHeight(), r, t);
        }
        if (contentView != null) {
            contentView.layout(l, t, r, b);
        }
    }

    private Point point = new Point();
    private Point point2 = new Point();
    private int CONSUMEBYUNKNOW = -1;
    private int CONSUMEBYSELF = 0;
    private int CONSUMEBYCHILD = 1;
    private int consumeMode = CONSUMEBYUNKNOW;

    //仅设置全局变量
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point.y = (int) ev.getY();
                mInitialMotionX = point.y;
                break;
            case MotionEvent.ACTION_MOVE:
                point2.y = (int) ev.getY();
                dy = point2.y - point.y;
                point.y = point2.y;
                break;
            case MotionEvent.ACTION_UP:
                consumeMode = CONSUMEBYUNKNOW;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    //判断是是否要进行中断事件(若子视图消费了事件,此方法会一直执行,若本视图消费了则下次不会调用此方法)
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (contentView == null || !(contentView instanceof AdapterView || contentView instanceof RecyclerView) || refreshMode == DONOTREFRESH) {
            consumeMode = CONSUMEBYCHILD;
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                pullMode = ajudgMode(dy);
                if (consumeMode == CONSUMEBYUNKNOW && pullMode != PULLUNKNOW) { //判断消费者是自己还是子视图,只需要判断一次
                    boolean attachToTop = false;
                    if (contentView instanceof ListView) {
                        ListView lv = (ListView) contentView;
                        attachToTop = lv.getFirstVisiblePosition() == 0 && lv.getChildAt(0).getTop() == 0;
                    } else if (contentView instanceof RecyclerView) {
                        RecyclerView.LayoutManager layoutManager = ((RecyclerView) contentView).getLayoutManager();
                        attachToTop = LinearLayoutManager.class.cast(layoutManager).findFirstVisibleItemPosition() == 0 && layoutManager.getChildAt(0).getTop() == 0;
                    } else if (contentView instanceof GridView) {
                        GridView rv = (GridView) contentView;
                        attachToTop = rv.getFirstVisiblePosition() == 0 && rv.getChildAt(0).getTop() == 0;
                    }
                    if (pullMode == PULLDOWNMODE && attachToTop) {
                        consumeMode = CONSUMEBYSELF;
                    } else {
                        consumeMode = CONSUMEBYCHILD;
                    }
                }
        }
        return consumeMode == CONSUMEBYSELF;
    }

    //处理本视图自己消费的事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (consumeMode == CONSUMEBYCHILD) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                comsumeActoinMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (-getScrollY() >= headerView.getHeight()) {
                    headerView.onRefreshing();
                    scroller.startScroll(0, getScrollY(), 0, -getScrollY() - headerView.getHeight(), 500);
                    if (iRefreshing != null&&!isRefreshing){
                        iRefreshing.onRefresh();
                        isRefreshing = true;
                    }
                } else {
                    scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                }
                invalidate();
                break;
        }
        return true;
    }

    private Scroller scroller;

    /**
     * 消费事件
     *
     * @param ev
     * @return
     */
    private boolean comsumeActoinMove(MotionEvent ev) {

        try {
            if (-getScrollY() >= MAXSCROLL || getScrollY() > 0) {
                return true;
            }
            if (Math.abs(getScrollY()) >= headerView.getHeight()) {
                headerView.onReleaseToRefresh();
            } else {
                headerView.onPullToRefresh();
            }
            headerView.onPullAnim((point2.y - mInitialMotionX) / 2.0f / headerView.getHeight());
            scrollBy(0, -dy / 2);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private final int PULLUNKNOW = -1;
    private final int PULLDOWNMODE = 0;
    private final int PULLUPMODE = 1;
    private int pullMode = PULLUNKNOW;

    /**
     * 判断上拉下拉模式
     *
     * @param dy
     * @return
     */
    private int ajudgMode(int dy) {
        if (dy > 10) {
            return PULLDOWNMODE;
        } else if (dy < -10) {
            return PULLUPMODE;
        } else {
            return PULLUNKNOW;
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 刷新完成
     */
    public void onRefreshComplete() {
        if (refreshMode != DONOTREFRESH) {
            if (headerView != null)
                headerView.onRefreshComplete();
            scroller.forceFinished(true);
            scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
            isRefreshing = false;
            invalidate();
        }
    }

    /**
     * 设置是否支持刷新
     *
     * @param mode
     */
    public void setRefreshMode(int mode) {
        this.refreshMode = mode;
    }

    /**
     * 设置自定义头部视图
     *
     * @param view
     */
    public void setHeaderLayout(PullToRefreshHeaderBase view) {
        removeView(this.headerView);
        this.headerView = view;
        addView(headerView);
        invalidate();
    }

    /**
     * 刷新时回调
     *
     * @param iRefreshing
     */
    public void setIRefreshing(IRefreshing iRefreshing) {
        this.iRefreshing = iRefreshing;
    }

    /**
     * 刷新通知拿数据
     */
    public interface IRefreshing {
        void onRefresh();
    }

}

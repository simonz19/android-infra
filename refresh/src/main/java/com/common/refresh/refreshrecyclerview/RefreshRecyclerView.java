package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.common.refresh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zou on 2016/3/22.
 * 带刷新和下拉功能的recyclerview,注意adapter必须使用HeaderAndFooterRecyclerViewAdapter
 * 基类只提供上拉加载,并提供下拉刷新接口,由子类去实现
 * 问题:1,swiperefresh冲突,item在页面未超出屏幕 下拉刷新会触发上拉加载
 */
public abstract class RefreshRecyclerView extends RelativeLayout {

    protected RecyclerView recyclerView;
    private int layoutManagerMode = -1;
    private final int GRIDLAYOUTMANAGER = 0;
    private final int LINEARLAYOUTMANAGER = 1;
    private final int STAGGEREDGRIDLAYOUTMANAGER = 2;
    private final int HORIZONTAL = LinearLayout.HORIZONTAL;
    private final int VERTICAL = LinearLayout.VERTICAL;
    private int orientation = VERTICAL;
    private final int MINCOLUMNS = 2;
    private int columns = MINCOLUMNS;

    /**
     * 刷新和加载模式
     */
    public enum Mode {
        DISABLE, ALL, REFRESH, LOAD_MORE
    }

    /**
     * 刷新模式
     */
    private Mode mode = Mode.ALL;

    /**
     * 一页item个数 默认为20个
     */
    private int pageSize = 20;

    /**
     * 下拉刷新和上拉加载回调
     */
    protected List<OnRecyclerRefreshListener> onRecyclerRefreshListener;

    /**
     * 内部计算用的scrollListener
     */
    private RecyclerView.OnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onScrollToEnd(View view) {
            if (!isSupportLoadMoreMode()) {
                return;
            }
            if (currentPullMode != MODEUP) {
                return;
            }
            if (recyclerView.getLayoutManager().getItemCount() < pageSize) { //总共的item个数小于规定的pagesize则不加载更多
                onLoadMoreEnd();
                return;
            }
            LoadingFooter.State state = getFooterState();
            if (state != LoadingFooter.State.Normal) {
                return;
            }
            if (onRecyclerRefreshListener != null && onRecyclerRefreshListener.size() > 0) {
                onLoadMoreLoading();
                for (int i = 0; i < onRecyclerRefreshListener.size(); i++) {
                    onRecyclerRefreshListener.get(i).onLoadMore(view);
                }
            }
        }

        @Override
        protected void onScroll(RecyclerView recyclerView, int dx, int dy) {
            onRecyclerScroll(recyclerView, dx, dy); //子类需要滑动监听如pinnedheader
        }
    };

    protected View view;

    public abstract int getRecyclerId();

    protected abstract int getResId();

    public abstract void onRefreshComplete();

    public abstract boolean isRefreshing();

    protected void onRecyclerScroll(RecyclerView recyclerView, int dx, int dy) {
    }

    public RefreshRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        view = inflate(getContext(), getResId(), this);
        recyclerView = (RecyclerView) view.findViewById(getRecyclerId());
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.recyclerRefresh, defStyleAttr, 0);
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.recyclerRefresh_recyclerRefreshMode) {
                    layoutManagerMode = typedArray.getInteger(attr, -1);
                } else if (attr == R.styleable.recyclerRefresh_recyclerRefreshOrientation) {
                    orientation = typedArray.getInteger(attr, VERTICAL);
                } else if (attr == R.styleable.recyclerRefresh_recyclerRefreshcolunms) {
                    columns = typedArray.getInteger(attr, MINCOLUMNS);
                }
            }
            typedArray.recycle();
        }
        setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(onScrollListener);
        if (layoutManagerMode == LINEARLAYOUTMANAGER) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else if (layoutManagerMode == GRIDLAYOUTMANAGER) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columns));
        } else if (layoutManagerMode == STAGGEREDGRIDLAYOUTMANAGER) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columns, orientation));
        }

    }

    private float downPoint;
    private int currentPullMode;
    private final int MODEUP = 1;
    private final int MODEDOWN = 2;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint = event.getY();
                currentPullMode = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPullMode == 0) {
                    if (event.getY() - downPoint > 10) {
                        currentPullMode = MODEDOWN;
                    } else if (event.getY() - downPoint < -10) {
                        currentPullMode = MODEUP;
                    }
                }
                downPoint = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 设置recycleview的模式
     *
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 获取layoutmanager
     *
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置item动画
     *
     * @param itemAnimator
     */
    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        recyclerView.setItemAnimator(itemAnimator);
    }

    /**
     * 设置分割线
     *
     * @param itemDecoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 设置刷新回调
     *
     * @param onRecyclerRefreshListener
     */
    @Deprecated
    public void setOnRecyclerRefreshListener(OnRecyclerRefreshListener onRecyclerRefreshListener) {
        if (this.onRecyclerRefreshListener == null) {
            this.onRecyclerRefreshListener = new ArrayList<OnRecyclerRefreshListener>();
        }
        this.onRecyclerRefreshListener.add(onRecyclerRefreshListener);
    }

    /**
     * 设置刷新回调
     *
     * @param onRecyclerRefreshListener
     */
    public void addOnRecyclerRefreshListener(OnRecyclerRefreshListener onRecyclerRefreshListener) {
        if (this.onRecyclerRefreshListener == null) {
            this.onRecyclerRefreshListener = new ArrayList<OnRecyclerRefreshListener>();
        }
        this.onRecyclerRefreshListener.add(onRecyclerRefreshListener);
    }

    /**
     * 获取footerview状态
     *
     * @return
     */
    public LoadingFooter.State getFooterState() {
        return RecyclerViewStateUtils.getFooterViewState(recyclerView);
    }

    /**
     * 正在加载更多
     */
    public void onLoadMoreLoading() {
        if (isSupportLoadMoreMode())
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Loading, null);
    }

    /**
     * 加载更多到底了
     */
    public void onLoadMoreEnd() {
        if (isSupportLoadMoreMode())
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd, null);
    }

    /**
     * 加载更多完毕
     */
    public void onLoadMoreComplete() {
        if (isSupportLoadMoreMode())
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal, null);

    }

    /**
     * 加载更多出错
     *
     * @param errorListener
     */
    public void onLoadMoreError(OnClickListener errorListener) {
        if (isSupportLoadMoreMode())
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.NetWorkError, errorListener);
    }

    /**
     * 判断刷新模式是否支持上拉加载
     *
     * @return
     */
    public boolean isSupportLoadMoreMode() {
        return this.mode == Mode.ALL || this.mode == Mode.LOAD_MORE;
    }

    /**
     * 设置滑动监听
     *
     * @param onScrollListener
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    /**
     * 设置一页多少item
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 刷新模式
     *
     * @param mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * 获得recyclerview
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}

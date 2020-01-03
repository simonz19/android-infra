package com.common.refresh.refreshrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.common.refresh.R;


/**
 * Created by zou on 2016/3/22.
 * 带刷新和下拉功能的recyclerview,注意adapter必须使用HeaderAndFooterRecyclerViewAdapter
 */
public abstract class RefreshRecyclerView extends RelativeLayout {

    protected RecyclerView recyclerView;
    private int layoutManagerMode = -1;
    private final int GRIDLAYOUTMANAGER = 0;
    private final int LINEARLAYOUTMANAGER = 1;
    private final int STAGGEREDGRIDLAYOUTMANAGER = 2;
    private int orientation = LinearLayout.VERTICAL;
    private final int HORIZONTAL = LinearLayout.HORIZONTAL;
    private final int VERTICAL = LinearLayout.VERTICAL;
    private final int MINCOLUMNS = 2;
    private int columns = MINCOLUMNS;

    protected OnRecyclerRefreshListener onRecyclerRefreshListener;

    private RecyclerView.OnScrollListener onScrollListener2 ;
    private RecyclerView.OnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if (state == LoadingFooter.State.TheEnd||state == LoadingFooter.State.NetWorkError) {
                Log.d(SwipRefreshRecyclerView.class.getSimpleName(), "no data anly more or occurde error");
                return;
            }
            if (onRecyclerRefreshListener != null&&state == LoadingFooter.State.Normal) {
                RecyclerViewStateUtils.setFooterViewState(getContext(), recyclerView, LoadingFooter.State.Loading, null);
                onRecyclerRefreshListener.onLoadMore(view);
            }
        }

        @Override
        protected void onScroll(RecyclerView recyclerView, int dx, int dy) {
            if(onScrollListener2!=null) onScrollListener2.onScrolled(recyclerView,dx,dy); //外界需要滑动监听
            onRecyclerScroll(recyclerView, dx, dy) ; //子类需要滑动监听如pinnedheader
        }
    };

    protected View view;

    public abstract int getRecyclerId() ;
    protected abstract int getResId();
    public abstract void onRefreshComplete()  ;

    protected void onRecyclerScroll(RecyclerView recyclerView, int dx, int dy){}

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
        initRecyclerView();
    }

    private void initRecyclerView() {
        setItemAnimator(new DefaultItemAnimator()) ;
        recyclerView.setOnScrollListener(onScrollListener);
        if (layoutManagerMode == LINEARLAYOUTMANAGER) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),orientation)) ;
        } else if (layoutManagerMode == GRIDLAYOUTMANAGER) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columns));
//            recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext())) ;
        } else if (layoutManagerMode == STAGGEREDGRIDLAYOUTMANAGER) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columns, orientation));
        }
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
    public void setOnRecyclerRefreshListener(OnRecyclerRefreshListener onRecyclerRefreshListener) {
        this.onRecyclerRefreshListener = onRecyclerRefreshListener;
    }

    /**
     * 设置footerview的状态
     *
     * @param state
     * @param errorListener
     */
    public void setLoadState(LoadingFooter.State state, View.OnClickListener errorListener) {
        RecyclerViewStateUtils.setFooterViewState(getContext(), recyclerView, state, errorListener);
    }

    /**
     * 获取layoutmanager
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager(){
        return recyclerView.getLayoutManager() ;
    }

    /**
     * 设置滑动监听
     * @param onScrollListener
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener2 = onScrollListener;
    }

    /**
     * 设置刷新模式(有或没有)
     */
    protected void setRefreshMode(boolean supportRefresh){

    }

}

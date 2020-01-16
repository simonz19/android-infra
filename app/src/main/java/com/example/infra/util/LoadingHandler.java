package com.example.infra.util;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.common.refresh.refreshrecyclerview.OnRecyclerRefreshListener;
import com.common.refresh.refreshrecyclerview.RefreshRecyclerView;
import com.example.infra.common.entity.CommonResponse;
import com.example.infra.common.entity.IListEntity;
import com.example.infra.common.http.DefaultDisposableObserver;
import com.example.infra.common.http.IRetrofitCallServer;
import com.example.infra.common.http.RetrofitErrorType;
import com.example.infra.common.http.RetrofitExcutor;
import com.example.infra.common.ui.lazyload.IProgressListener;

import java.util.List;

public class LoadingHandler<T> implements DefaultLifecycleObserver {
    private IRetrofitCallServer<T> iRetrofitCallServer;
    private OnRefreshListener onRefreshListener;
    private PageNumHolder pageNumHolder;
    private int mode = 0;
    private final int MODE_COMMON = 0;
    private final int MODE_LIST = 1;
    private RefreshRecyclerView listView;
    private IProgressListener iLoading;
    private boolean showEmpty;

    private enum LoadMode {
        refresh, more;
    }

    /**
     * 重新请求
     */
    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (iLoading != null) {
                iLoading.showLoading();
            }
            loadData();
        }
    };

    private LoadingHandler(IRetrofitCallServer<T> iRetrofitCallServer, OnRefreshListener onRefreshListener,
                           PageNumHolder pageNumHolder, RefreshRecyclerView listView,
                           IProgressListener iProgressListener, boolean showEmpty) {
        this.iRetrofitCallServer = iRetrofitCallServer;
        this.onRefreshListener = onRefreshListener;
        this.iLoading = iProgressListener;
        this.pageNumHolder = pageNumHolder;
        this.listView = listView;
        this.showEmpty = showEmpty;
        mode = MODE_LIST;
        init();
    }

    private LoadingHandler(IRetrofitCallServer<T> iRetrofitCallServer, OnRefreshListener onRefreshListener,
                           IProgressListener iProgressListener, boolean showEmpty) {
        this.iRetrofitCallServer = iRetrofitCallServer;
        this.onRefreshListener = onRefreshListener;
        this.iLoading = iProgressListener;
        this.showEmpty = showEmpty;
        mode = MODE_COMMON;
        init();
    }

    private void init() {
        if (mode == MODE_LIST) {
            listView.addOnRecyclerRefreshListener(new OnRecyclerRefreshListener() {
                @Override
                public void onRefresh() {
                    if (pageNumHolder != null) {
                        pageNumHolder.pageNum = pageNumHolder.initialPageNum;
                    }
                    loadDataSilent();
                }

                @Override
                public void onLoadMore(View view) {
                    if (pageNumHolder == null) {
                        listView.onLoadMoreComplete(); //设置了上拉加载模式,但是忘记设置pagenum
                        return;
                    }
                    pageNumHolder.pageNum++;
                    loadData(LoadMode.more, -1);
                }
            });
        }
    }

    /**
     * 加载更多
     */
    private DefaultDisposableObserver<T> more;

    /**
     * create observer for more action
     *
     * @return
     */
    private DefaultDisposableObserver<T> getMoreDisposableObserver() {
        return new DefaultDisposableObserver<T>() {
            @Override
            protected void onSuccess(T result) {
                if (handleResult(result, false) && onRefreshListener != null) {
                    onRefreshListener.onLoadComplete(result); //去装配数据
                }
            }

            @Override
            protected void onFailed(RetrofitErrorType errorType, String errorMsg) {
                listView.onLoadMoreError(v -> {
                    listView.onLoadMoreLoading();
                    loadData(LoadMode.more, -1);
                });
            }
        };
    }

    private DefaultDisposableObserver<T> refresh;

    /**
     * create observer for refresh action
     *
     * @return
     */
    private DefaultDisposableObserver<T> getRefreshDisposableObserver() {
        return new DefaultDisposableObserver<T>() {

            @Override
            protected void onSuccess(T result) {
                if (handleResult(result, true) && onRefreshListener != null) {
                    onRefreshListener.onRefreshComplete(result); //去装配数据
                }
            }

            @Override
            protected void onFailed(RetrofitErrorType errorType, String errorMsg) {
                if (iLoading != null) {
                    iLoading.showError(retryListener);
                }
            }
        };
    }

    private boolean handleResult(T result, boolean refreshMode) {
        if (mode == MODE_LIST) {
            listView.onRefreshComplete();
            List results = null;
            if (result instanceof List) {
                results = (List) result;
            } else if (result instanceof IListEntity) {
                results = ((IListEntity) result).getList();
            } else if (result instanceof CommonResponse) {
                Object data = ((CommonResponse) result).getData();
                if (data instanceof IListEntity) {
                    results = ((IListEntity) data).getList();
                }
            }
            if (CollectionUtils.isEmpty(results)) {
                if (refreshMode) {
                    if (iLoading != null) {
                        if (showEmpty) {
                            iLoading.showEmpty(retryListener);
                        } else {
                            iLoading.showContent();
                        }
                    }
                } else {
                    pageNumHolder.pageNum--;
                    listView.onLoadMoreEnd();
                }
                ToastUtil.showMessage("暂无更多数据!");
                return false;
            } else {
                if (refreshMode && iLoading != null) {
                    iLoading.showContent();
                }
                listView.onLoadMoreComplete();
            }
        } else if (mode == MODE_COMMON) {
            iLoading.showContent();
        }
        return true;
    }

    /**
     * 第一次加载
     */
    public void loadData() {
        loadData(LoadMode.refresh, 0);
    }

    /**
     * 二次加载
     */
    public void loadData2() {
        loadData(LoadMode.refresh, 1);
    }

    /**
     * 静默加载
     */
    public void loadDataSilent() {
        loadData(LoadMode.refresh, -1);
    }

    private void loadData(LoadMode loadMode, int mode) {
        if (loadMode == LoadMode.refresh && pageNumHolder != null) {
            pageNumHolder.pageNum = pageNumHolder.initialPageNum;
        }
        if (iLoading != null) {
            if (mode == 0) {
                iLoading.showLoading();
            } else if (mode == 1) {
                iLoading.showLoading2();
            }
        }
        cancel();
        DefaultDisposableObserver<T> observerImpl;
        if (loadMode == LoadMode.refresh) {
            observerImpl = refresh = getRefreshDisposableObserver();
        } else {
            observerImpl = more = getMoreDisposableObserver();
        }
        RetrofitExcutor.get().excute(iRetrofitCallServer.onCallServer(), observerImpl);
    }

    /**
     * 取消订阅,以这种方式取消retrofit的请求
     */
    public void cancel() {
        if (refresh != null && !refresh.isDisposed())
            refresh.dispose();
        if (more != null && !more.isDisposed())
            more.dispose();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        listView = null;
        iLoading = null;
        cancel();
    }

    public static class Builder<V> {
        private IRetrofitCallServer<V> iRetrofitCallServer;
        private OnRefreshListener onRefreshListener;
        private PageNumHolder pageNumHolder;
        private RefreshRecyclerView listView;
        private IProgressListener iProgressListener;
        private boolean showEmpty = true;

        public Builder<V> setListView(RefreshRecyclerView listView) {
            this.listView = listView;
            return this;
        }

        public Builder<V> setPageNumHolder(PageNumHolder pageNumHolder) {
            this.pageNumHolder = pageNumHolder;
            return this;
        }

        public Builder<V> setOnRefreshListener(OnRefreshListener onRefreshListener) {
            this.onRefreshListener = onRefreshListener;
            return this;
        }

        public Builder<V> setIProgressListener(IProgressListener iProgressListener) {
            this.iProgressListener = iProgressListener;
            return this;
        }

        public Builder<V> setIRetrofitCallServer(IRetrofitCallServer<V> iRetrofitCallServer) {
            this.iRetrofitCallServer = iRetrofitCallServer;
            return this;
        }

        public Builder<V> setShowEmpty(boolean showEmpty) {
            this.showEmpty = showEmpty;
            return this;
        }

        public LoadingHandler<V> build() {
            if (iRetrofitCallServer == null) {
                throw new IllegalStateException("iRetrofitCallServer must not be null");
            }

            if (listView == null) {
                return new LoadingHandler<V>(iRetrofitCallServer, onRefreshListener, iProgressListener, showEmpty);
            } else {
                return new LoadingHandler<V>(iRetrofitCallServer, onRefreshListener, pageNumHolder, listView, iProgressListener, showEmpty);
            }
        }
    }

    public static class PageNumHolder {
        public PageNumHolder() {
        }

        public PageNumHolder(int pageNum) {
            this.pageNum = pageNum;
            this.initialPageNum = pageNum;
        }

        public int pageNum;
        private int initialPageNum;
    }

    public interface OnRefreshListener<T> {
        void onRefreshComplete(T t);

        void onLoadComplete(T t);
    }

}

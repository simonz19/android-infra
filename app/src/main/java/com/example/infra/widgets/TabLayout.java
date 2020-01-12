package com.example.infra.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.infra.R;

public class TabLayout extends LinearLayout {

    private OnSelcetedListener onSelcetedListener;
    private View currentSelectedItem;

    public interface OnSelcetedListener {
        void onSelected(int position);
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == currentSelectedItem) {
                return;
            }
            v.setSelected(true);
            if (currentSelectedItem != null)
                currentSelectedItem.setSelected(false);
            if (onSelcetedListener != null)
                onSelcetedListener.onSelected(indexOfChild(v));
            currentSelectedItem = v;
        }
    };

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    /**
     * 添加tab
     *
     * @param view
     */
    public void addTab(View view) {
        FrameLayout frame = new FrameLayout(view.getContext());
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        frameParams.gravity = Gravity.CENTER;
        view.setLayoutParams(frameParams);
        frame.addView(view);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        frame.setLayoutParams(params);
        addView(frame);

        frame.setOnClickListener(clickListener);
        invalidate();
    }

    /**
     * 获得指定位置的tab
     *
     * @param position
     */
    public View getTab(int position) {
        return ViewGroup.class.cast(getChildAt(position)).getChildAt(0);
    }

    /**
     * 选中指定position的view
     *
     * @param position
     */
    public void select(int position) {
        if (getChildAt(position) == currentSelectedItem) return;
        getChildAt(position).setSelected(true);
        if (currentSelectedItem != null)
            currentSelectedItem.setSelected(false);
        currentSelectedItem = getChildAt(position);
    }

    /**
     * 设置选中的监听
     *
     * @param listener
     */
    public void setOnSelcetedListener(OnSelcetedListener listener) {
        this.onSelcetedListener = listener;
    }

    /**
     * 获得selected的item
     *
     * @return
     */
    public int getSelectedPosition() {
        return indexOfChild(currentSelectedItem);
    }
}

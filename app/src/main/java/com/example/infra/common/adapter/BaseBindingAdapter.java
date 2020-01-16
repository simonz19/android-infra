/*
 * Copyright 2018-2019 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.infra.common.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.common.refresh.refreshrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends HeaderAndFooterRecyclerViewAdapter<M> {

    public BaseBindingAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, final int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, this.datas.get(position), holder);
    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
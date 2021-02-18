package com.dpdp.base_moudle.weight;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-08
 * <p>
 * Summary: https://www.cnblogs.com/whycxb/p/9329660.html
 */
public class WrapRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_FOOT = 200;
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private final View footView;
    private boolean enableLoadMore = true;

    public WrapRecyclerViewAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, View footView, boolean enableLoadMore) {
        this.adapter = adapter;
        this.footView = footView;
        this.enableLoadMore = enableLoadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (adapter != null && viewType != ITEM_TYPE_FOOT) {
            return adapter.onCreateViewHolder(parent, viewType);
        }
        return new FootViewHolder(footView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (adapter != null) {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter == null ? 0 : adapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (enableLoadMore) {
            if (position == adapter.getItemCount()) {
                return ITEM_TYPE_FOOT;
            }
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams && isFootView(holder.getLayoutPosition())) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
        adapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        adapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        adapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return adapter.onFailedToRecycleView(holder);
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        adapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        adapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isFootView(position) ? 1 : gridLayoutManager.getSpanCount();
                }
            });
        }
        adapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        adapter.onDetachedFromRecyclerView(recyclerView);
    }


    private static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private boolean isFootView(int position) {
        if (enableLoadMore) {
            return position == adapter.getItemCount();
        }
        return false;
    }
}

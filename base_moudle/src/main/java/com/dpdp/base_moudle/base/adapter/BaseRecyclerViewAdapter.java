package com.dpdp.base_moudle.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-20
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<?> list;
    private final Context context;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setListData(List list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addListData(List list) {
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (getItemLayoutId() == 0) {
            return new BaseRVViewHolder(new View(context));
        }
        View itemView = LayoutInflater.from(context).inflate(getItemLayoutId(), parent, false);
        return new BaseRVViewHolder(itemView);
    }

    protected abstract int getItemLayoutId();

    protected abstract void onItemClick(RecyclerView.ViewHolder holder, Object itemData, int position);

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, Object itemData, int position);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder(holder, list.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(holder, list.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

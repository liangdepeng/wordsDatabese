package com.example.administrator.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.words.R;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-06
 * <p>
 * Summary: 主页
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            return new TypeOneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type_one_layout, parent, false));
        } else if (viewType == 1) {
            return new TypeTwoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type_two_layout, parent, false));
        } else {
            return new NormalViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type_list_layout, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeOneViewHolder) {

        } else if (holder instanceof TypeTwoViewHolder) {

        } else if (holder instanceof NormalViewHolder) {

        } else {
            // default

        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class TypeOneViewHolder extends RecyclerView.ViewHolder {
        public TypeOneViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class TypeTwoViewHolder extends RecyclerView.ViewHolder {
        public TypeTwoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

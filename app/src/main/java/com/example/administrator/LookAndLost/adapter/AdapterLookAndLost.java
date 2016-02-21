package com.example.administrator.LookAndLost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class AdapterLookAndLost extends RecyclerView.Adapter<AdapterLookAndLost.ViewHolderLookAndLost> {

    Context context;
    List<LookAndLostEntity> list;

    public AdapterLookAndLost(List<LookAndLostEntity> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public ViewHolderLookAndLost onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderLookAndLost holder = new ViewHolderLookAndLost(LayoutInflater.from(
                context).inflate(R.layout.item_look_and_lost, parent,
                false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public void onBindViewHolder(ViewHolderLookAndLost holder, int position) {
        holder.tv.setText(list.get(position).getTitle());
    }

    class ViewHolderLookAndLost extends RecyclerView.ViewHolder
    {

        TextView tv;

        public ViewHolderLookAndLost(View view)
        {
            super(view);
            tv= (TextView) view.findViewById(R.id.item_look_and_lost_tv);
        }
    }
}
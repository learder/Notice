package com.example.administrator.LookAndLost.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.LookAndLost.AnimHelp;
import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class AdapterLookAndLost extends RecyclerView.Adapter<AdapterLookAndLost.ViewHolderLookAndLost> {

    Context context;
    List<LookAndLostEntity> list;

    private static final int DELAY = 138;
    private int mLastPosition = -1;

    public AdapterLookAndLost(List<LookAndLostEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setDatas(List<LookAndLostEntity> list) {
        this.list = list;
        if (BuildConfig.DEBUG) Log.d("AdapterLookAndLost", list.toString());
        notifyDataSetChanged();
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
        return list == null ? 0 : list.size();
    }

    public LookAndLostEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolderLookAndLost holder, int position) {
        LookAndLostEntity entity = list.get(position);
        String title = entity.getTitle();
        holder.itemLookAndLostAdapterTitleTv.setText(title);
        if (entity.getImgs()!=null&&!entity.getImgs().isEmpty()){
            String url=entity.getImgs().get(0).getImg();
            Uri uri = Uri.parse(url);
            holder.itemLookAndLostAdapterImgSdv.setImageURI(uri);
        }
        String address=entity.getAddress();
        holder.itemLookAndLostAdapterAddressTv.setText(address);
        showItemAnim(holder.view, position);
    }

    class ViewHolderLookAndLost extends RecyclerView.ViewHolder {

//        @InjectView(R.id.item_look_and_lost_adapter_title_tv)
        TextView itemLookAndLostAdapterTitleTv;
//        @InjectView(R.id.item_look_and_lost_adapter_img_sdv)
        SimpleDraweeView itemLookAndLostAdapterImgSdv;
//        @InjectView(R.id.item_look_and_lost_adapter_address_tv)
        TextView itemLookAndLostAdapterAddressTv;
        View view;

        public ViewHolderLookAndLost(View view) {
            super(view);
            this.view = view;
//            ButterKnife.inject(view);
            itemLookAndLostAdapterAddressTv= (TextView) view.findViewById(R.id.item_look_and_lost_adapter_address_tv);
            itemLookAndLostAdapterImgSdv= (SimpleDraweeView) view.findViewById(R.id.item_look_and_lost_adapter_img_sdv);
            itemLookAndLostAdapterTitleTv= (TextView) view.findViewById(R.id.item_look_and_lost_adapter_title_tv);
        }
    }

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            AnimHelp.flyInAnim(view);
            mLastPosition = position;
        }
    }
}

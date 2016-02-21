package com.example.administrator.LookAndLost.adapter;


import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.entity.CityEntity;
import com.example.administrator.LookAndLost.view.WifiPinnedSectionListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市查询列表
 * @author yanjuegong
 *
 */
public class LocationCityListViewAdapter extends BaseAdapter implements
		OnClickListener, WifiPinnedSectionListView.PinnedSectionListAdapter {
	
	private Context context;
	private List<CityEntity> list;
	private SparseIntArray positionOfSection;
	private SparseIntArray sectionOfPosition;
	
	public LocationCityListViewAdapter(Context context, List<CityEntity> list){
		this.context=context;
		this.list=list;
		
	}

	@Override
	public int getCount() {
		if (list!=null) {
			return list.size();
		}
		return 0;
	}
	
	public void setData(List<CityEntity> list){
		this.list=list;
		notifyDataSetChanged();
	}

	@Override
	public CityEntity getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return CityEntity.TYPE_TITLE==viewType;
	}
	
	@Override
	public int getItemViewType(int position) {
		return list.get(position).type;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			switch (getItemViewType(position)) {
			case CityEntity.TYPE_NORMAL:
				view = View.inflate(context,
						R.layout.location_city_activity_listview_item, null);
				holder.city_name_tv=(TextView) view.findViewById(R.id.location_city_activity_listview_item_city_name);
				break;

			case CityEntity.TYPE_TITLE:
				view = View.inflate(context,
						R.layout.location_city_activity_listview_item_title, null);
				holder.city_name_tv = (TextView) view
						.findViewById(R.id.location_city_activity_listview_item_title);
				break;
			}
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		CityEntity item = getItem(position);
		String city_name = item.getFullName();
		switch (getItemViewType(position)) {
		case CityEntity.TYPE_NORMAL:
			holder.city_name_tv.setText(city_name);
			break;
		case CityEntity.TYPE_TITLE:
			holder.city_name_tv.setText(city_name);
			break;
	}
		return view;
	}
	
	public class ViewHolder {
		TextView city_name_tv;
		TextView location_mode;//定位模式
		
		ImageView city_left_iv;
	}
	
	//记录分组信息
	public Object[] getSections() {
			positionOfSection = new SparseIntArray();
			sectionOfPosition = new SparseIntArray();
			int count = getCount();
			List<String> list = new ArrayList<String>();
			list.add("A");
			positionOfSection.put(0, 0);
			sectionOfPosition.put(0, 0);
			for (int i = 1; i < count; i++) {

				String letter = getItem(i).getHeader();
				int section = list.size()-1;
				if (list.get(section) != null && !list.get(section).equals(letter)) {
					list.add(letter);
					section++;
					positionOfSection.put(section, i);
				}
				sectionOfPosition.put(i, section);
			}
			
			return list.toArray(new String[list.size()]);
		}
	
	public int getSectionForPosition(int position) {
		return sectionOfPosition.get(position);
	}
	public int getPositionForSection(int section) {
		return positionOfSection.get(section);
	}

	@Override
	public void onClick(View v) {
	}

}

package com.example.administrator.LookAndLost.utils.manger;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.entity.CityEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 地址查询线程 
 * 
 * 城市数据的管理
 * 
 * 当前定位的城市，在这里加入
 * @author yanjuegong
 *
 */
public class LocationCitySearchQuery implements TextWatcher {
	
	private String TAG="LocationCityActivitySearchQuery";
	private Context context;
	private SearchListTask curSearchTask = null;
	private List<CityEntity> contactList;
	private List<CityEntity> filterList = new ArrayList<>();
	private Object searchLock = new Object();
	private boolean inSearchMode = false;
	private EditText edit;
	private StringBuffer currentCity=new StringBuffer();
	public SearchQueryCallBack callBack;
	
	public interface SearchQueryCallBack{
		void searchQueryCallBack(boolean isSearchMode, List<CityEntity> contactList);
	}
	
	public void setSearchListener(SearchQueryCallBack queryCallBack){
		this.callBack=queryCallBack;
	}
	
	public LocationCitySearchQuery(Context context, EditText edit) {
		this.edit=edit;
		this.context=context;
		if (edit!=null) {
			this.edit.addTextChangedListener(this);	
		}else {
			if (BuildConfig.DEBUG)
				Log.d("LocationCitySearchQuery", "LocationCityActivitySearchQuery is empty");
		}

	}
	
	public List<CityEntity> getCityEntityList(){
		List<CityEntity> list;
		list=LocationSourceManage.getInstance(context).getCityList();
		//添加当前城市列表，注意，城市跟模式，只使用逗号分开
		//这么做是因为我不想再给对象增加一个字段，以及再传递一个值！！
//		if (list!=null) {
//			CityEntity cityEntity=new CityEntity();
//			cityEntity.setFullName(currentCity.toString());
//			cityEntity.type=CityEntity.TYPE_CURRENT_CITY;
//			list.add(0, cityEntity);
//		}
		return list;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		String searchString = edit.getText()
				.toString().toUpperCase();
		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
			try {
				curSearchTask.cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		curSearchTask = new SearchListTask(callBack);
		curSearchTask.execute(searchString);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// do nothing
	}

	private class SearchListTask extends AsyncTask<String, Void, String> {
		private SearchQueryCallBack callBack;
		
		
		public SearchListTask(SearchQueryCallBack callBack) {
			this.callBack=callBack;
		}

		@Override
		protected String doInBackground(String... params) {
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode) {
				if (contactList==null) {
					contactList=getCityEntityList();
				}
				for (CityEntity item : contactList) {
					if (item.type!=CityEntity.TYPE_TITLE) {
						String fullName=item.getFullName().toUpperCase();
						boolean isPinyin = fullName
								.indexOf(keyword) > -1;
						String nickName=item.getNickName().toUpperCase();
						boolean isChinese=false;
						if (!nickName.equals("")) {
							isChinese = item.getNickName().indexOf(keyword) > -1;	
						}
						if (isPinyin || isChinese) {
							filterList.add(item);
						}
					}
					
				}

			}
			return null;
		}

		protected void onPostExecute(String result) {

			synchronized (searchLock) {
				if (inSearchMode) {
					//搜索模式
					if (callBack!=null) {
						callBack.searchQueryCallBack(inSearchMode,filterList);
					}
				} else {
					//全局模式
					if (callBack!=null) {
						callBack.searchQueryCallBack(inSearchMode,contactList);
					}
				}
				
			}

		}
	}

}

package com.example.administrator.LookAndLost.utils.manger;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.entity.CityEntity;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.FileUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LocationSourceManage {
	public static LocationSourceManage instance;
	private DbUtils db;
	
	public LocationSourceManage(Context context) {
		String path=FileUtil.createFileDir("dbDir/").getPath();
		db=DbUtils.create(context, path, Constants.LOCATION_CITY_DB_NAME_DB);
//		db=DbUtils.create(context, path, "prov_city_area_street.db");
		db.configDebug(BuildConfig.DEBUG);
		db.configAllowTransaction(true);
		instance=this;
	}
	
	public static LocationSourceManage getInstance(Context context){
		if (instance==null) {
			new LocationSourceManage(context);
		}
		return instance;
	}
	/**
	 * 获取全部省份信息
	 * @param
	 * @return
	 */
	public List<CityEntity> getProvince(){
		Selector selector=Selector.from(CityEntity.class).where(WhereBuilder.b("level", "=", 1));
		return queryDb(selector);
	}
	
	/**
	 * 获取全部城市列表
	 * @return
	 */
	public List<CityEntity> getCityList(){
		Selector selector=Selector.from(CityEntity.class).where(WhereBuilder.b("level", "=", 2));
		List<CityEntity> list=queryDb(selector);
		list=sortCityList(list);
		list=setLetter(list);
		return list;
	}
	
	/**
	 * 根据城市名称 来获取城市实体
	 * @param cityName
	 * @return
	 */
	public CityEntity getCityEntity(String cityName){
		if (TextUtils.isEmpty(cityName)) {
			return null;
		}
		Selector selector=Selector.from(CityEntity.class).where(WhereBuilder.b("fullName", "=", cityName));
		List<CityEntity> list=queryDb(selector);
		CityEntity entity=null;
		if (list!=null&&!list.isEmpty()) {
			entity=list.get(0);
		}
		return entity;
	}
	
	/**
	 * 排序
	 * 
	 */
	private List<CityEntity> sortCityList(List<CityEntity> list){
		if (list==null) {
			return null;
		}
		Collections.sort(list, new Comparator<CityEntity>() {

			@Override
			public int compare(CityEntity c1, CityEntity c2) {
				return c1.getHeader().compareTo(c2.getHeader());
			}
		});
		return list;
	}
	
	/**
	 * 复制上大写字母
	 * @param list
	 * @return
	 */
	private List<CityEntity> setLetter(List<CityEntity> list){
		if (list==null) {
			return null;
		}
		String lastLetter="";
		for (int i = 0; i < list.size(); i++) {
			list.get(i).type=CityEntity.TYPE_NORMAL;//设置为正常状态
			String header=list.get(i).getHeader();
			if (!header.equals(lastLetter)) {
				CityEntity cityEntity=new CityEntity();
				cityEntity.setHeader(header);
				cityEntity.setNickName("");
				cityEntity.setFullName(header);
				cityEntity.type=CityEntity.TYPE_TITLE;
				list.add(i, cityEntity);
				lastLetter=header;
			}
		}
		return list;
	}
	
	/**
	 * 获取该级别下面的子类
	 * 如：传入省实体，可以获得市的列表
	 * @param entity 数据库查询出来的实体
	 * @return
	 */
	public List<CityEntity> getChildList(CityEntity entity){
		if (entity==null) {
			return null;
		}
		int code=entity.getCode();
		if (code==-1) {
			entity=getCityEntity(entity.getFullName());
			if (entity!=null) {
				code=entity.getCode();
			}
		}
		if (code!=-1) {
			Selector selector=Selector.from(CityEntity.class).where(WhereBuilder.b("parentId", "=", code));
			return queryDb(selector);
		}else {
			return null;
		}
		
	}
	
	/**
	 * 查询数据库
	 * @return
	 */
	private List<CityEntity> queryDb(Selector selector){
		List<CityEntity> list=null;
		try {
			list = db.findAll(selector);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}

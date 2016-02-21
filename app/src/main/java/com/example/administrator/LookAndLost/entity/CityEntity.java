package com.example.administrator.LookAndLost.entity;

import android.text.TextUtils;


import java.io.Serializable;

/**
 * 城市实体
 * 
 * 每个实体 都由自身编码，父编码，行政等级构成
 * 父编码表示行政等级比自身高的编码，之间可以相互查找
 * 详见com.cmcc.wlan.yan.LocationSourceManage.class
 * 该类提供查询工具
 * 
 * @author yanjuegong
 * 
 */
public class CityEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7373870405346269869L;
	private int id;
	public static final int TYPE_NORMAL=0;//正常城市
	public static final int TYPE_TITLE = 1;//标记是标题还是列表
	public static final int TYPE_CURRENT_CITY=2;//当前定位城市
	public static final int TYPE_SELECTED=3;//选中后
	public static final int TYPE_ALL_NORMAL=4; //用来标记 筛选列表中 “所有地区” 颜色加红的正常标记，方便设置与正常不一样的状态
	public static final int TYPE_ALL_SELECTED=5;//用来标记筛选列表中 “所有地区” 颜色加工的选中标记，方便更变回来
	public int type = TYPE_NORMAL;// 0表示的是正常的列表,1表示是标题，2表示当前城市
	private String fullName="";//城市的全称
	private String nickName="";//城市首字母，用于查找
	private String header;// 城市的标题头，用于右侧的检索
	private int code=-1; //城市编码code
	private int parentId=-1; //父编码，用于查找上一级
	private int level=-1;//行政等级 1：省，2：市，3：区县，4：街道
	
	public CityEntity(){
		
	}

	public CityEntity(String nickName, String fullName) {
		super();
		this.nickName = nickName;
		this.setFullName(fullName);
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHeader() {
		if (TextUtils.isEmpty(header)) {
			if (!TextUtils.isEmpty(getNickName())) {
				header=getNickName().substring(0, 1);
			}
		}
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public boolean equals(Object o) {
		CityEntity i=(CityEntity) o;
		if (i!=null&&i.getFullName().equals(getFullName())) {
			return true;
		}
		return super.equals(o);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id：").append(getId()).append("--fullName：").append(getFullName()).append("--nickName：")
				.append(getNickName()).append("--header：").append(getHeader())
				.append("--code：").append(getCode()).append("--parentId：").append(getParentId()).append("--level：").append(getLevel()).append("\n");
		return sb.toString();
	}

}

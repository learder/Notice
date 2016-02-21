package com.example.administrator.LookAndLost.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SP辅助
 * 
 * @author yanjuegong
 *
 */
public class SPUtils {
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "share_data";

	/**
	 * 文件名
	 */
	private static final String CONFIG = "config";

	/**
	 * 保存布尔值
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public static synchronized void save2Sp(Context context, String key,
			boolean value) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 保存字符串
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public static synchronized void save2Sp(Context context, String key,
			String value) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	// /**
	// * 保存int数字
	// *
	// * @param key
	// * key
	// * @param value
	// * value
	// */
	// public static synchronized void save2Sp(Context context,String key, int
	// value) {
	// SharedPreferences sp = context.getSharedPreferences(CONFIG,
	// Context.MODE_PRIVATE);
	// Editor editor = sp.edit();
	// editor.putInt(key, value);
	// editor.commit();
	// }

	// /**
	// * 获取指定key的int
	// *
	// * @param key
	// * key
	// * @param defValue
	// * defValue
	// * @return 返回值
	// */
	// public static synchronized int get4Sp(Context context,String key, int
	// defValue) {
	// SharedPreferences sp = context.getSharedPreferences(CONFIG,
	// Context.MODE_PRIVATE);
	// return sp.getInt(key, defValue);
	// }

	/**
	 * 获取指定key的boolean
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return 返回值
	 */
	public static synchronized boolean get4Sp(Context context, String key,
			boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 获取指定key的String
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return 返回值
	 */
	public static synchronized String get4Sp(Context context, String key,
			String defValue) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	/**
	 * 获取指定key的long
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return 返回值
	 */
	public static synchronized long get4Sp(Context context, String key,
			long defValue) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		return sp.getLong(key, defValue);
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

    /**
     * 保存int数字
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public static synchronized void save2Sp(Context context,String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    /**
     * 保存long数字
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public static synchronized void save2Sp(Context context,String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }
	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

    /**
     * 获取指定key的int
     * 
     * @param key
     *            key
     * @param defValue
     *            defValue
     * @return 返回值
     */
    public static synchronized int get4Sp(Context context,String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}



	/**
	 * 获取存储的支付结果
	 *
	 * @param context
	 * @return
	 */
	public static int getPayResult(Context context) {
		return get4Sp(context, "payResult", 0);
	}

	/**
	 * 存储买单的id
	 *
	 * @param context
	 * @return
	 */
	public static void setBillId(Context context, int billId) {
		save2Sp(context, "billId", billId);
	}


	/**
	 * 获取买单的id
	 *
	 * @param context
	 * @return
	 */
	public static int getBillId(Context context) {
		return get4Sp(context, "billId", 0);
	}

	/**
	 * 设置支付结果
	 *
	 * @param context
	 * @return
	 */
	public static void setPayResult(Context context, int result) {
		save2Sp(context, "payResult", result);
	}



	/**
	 * 查询某个key是否已经存在
	 *
	 * @param context
	 * @return
	 */
	public static boolean isFirstLogin(Context context) {
		return get4Sp(context, "firstLogin", true);
	}

	/**
	 * 查询某个key是否已经存在
	 *
	 * @param context
	 * @return
	 */
	public static void setFirstLogin(Context context) {
		save2Sp(context, "firstLogin", false);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}
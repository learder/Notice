package com.example.administrator.LookAndLost.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.LookAndLost.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @Description: BaseFileUtil
 * @author: 
 * @date: 2013-7-26
 */
public class BaseFileUtil {

	private static final String TAG = BaseFileUtil.class.getSimpleName();

	/**
	 * 是否存在SD卡
	 */
	public static boolean isRemovedSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED);
	
	/**
	 * SDCard存储路径
	 */
	public final static String sdCardPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "SDCard";

	/**
	 * 读取流
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception{

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while( (len=inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 创建文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static File createFileIfNeed(File file) throws IOException {
		if (!file.exists()) {
			File parentDictionary = file.getParentFile();

			if (parentDictionary!=null && !parentDictionary.exists()) {
				parentDictionary.mkdirs();
			}

			if (file.createNewFile()){
				return file;
			}else{
				if (BuildConfig.DEBUG) Log.d(TAG, "file:" + file.getPath() + "  create failed.");
				return null;
			}
		}else{
			return file;
		}
	}

	/**
	 * 删除手机上的某个文件
	 * @param context
	 * @param fileName
	 */
	public static void deletePhoneFile(Context context,String fileName){
		File file = new File(context.getFilesDir(), fileName);
		deleteAll(file);
	}
	
	/**
	 * 删除文件 或 删除文件夹下的全部文件和文件夹
	 * @param needDeleteFile
	 */
	public static void deleteAll(File needDeleteFile) {

		if (!needDeleteFile.exists()){
			return;
		}else{
			if (needDeleteFile.isFile()) {
				needDeleteFile.delete();
				return;
			}else{
				File[] files = needDeleteFile.listFiles();
				for (File file : files) {
					deleteAll(file);
				}
				needDeleteFile.delete();
			}
		}
	}

	/**
	 * 从SD卡中读取文件文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileFromSDCard(String fileName) throws Exception {

		File file = new File(sdCardPath, fileName);

		if (!file.exists()) {
			if (BuildConfig.DEBUG) Log.d(TAG, "file not found [fileName]:" + fileName);
			return null;
		}else{
			FileInputStream inStream = new FileInputStream(file);
			return readStream(inStream);
		}
	}
	
	/**
	 * 把文件保存到SDCard中
	 * @param fileName
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static boolean saveToSDCard(String fileName, byte[] content)throws Exception {

		if (BuildConfig.DEBUG) Log.d(TAG, "begin saveToSDCard....." + fileName);

		File file = new File(sdCardPath, fileName);
		file = createFileIfNeed(file);

		if (file == null) {
			if (BuildConfig.DEBUG) Log.d(TAG, "file save to SDCard failed...." + fileName);
			return false;
		}else{
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(content);
			outStream.close();
			return true;
		}
	}


	/**
	 * @Description: 以私有文件保存内容，保存到手机自带存储
	 * @param fileName     文件名称
	 * @param byteContent  文件内容
	 * @param context 
	 * @param mode   文件操作模式[Context.MODE_APPEND,Context.MODE_PRIVATE,Context.
	 *            MODE_WORLD_READABLE,Context.MODE_WORLD_WRITEABLE]
	 * @throws Exception
	 * @returnType void
	 */
	public static void saveToPhone(String fileName, byte[] byteContent,Context context, int mode) throws Exception {

		if (BuildConfig.DEBUG) Log.d(TAG, "begin saveToPhone....." + fileName);

		File file = new File(context.getFilesDir(), fileName);
		file = createFileIfNeed(file);

		FileOutputStream outStream = context.openFileOutput(file.getName(),mode);
		outStream.write(byteContent);
		outStream.close();
	}

	/**
	 * @Description: 读取手机自带存储设备上的文件内容 
	 * @param fileName
	 * @param context
	 * @return
	 * @throws Exception
	 * @returnType byte[]
	 */
	public static byte[] readFileFromPhone(String fileName, Context context) throws Exception {

		if (BuildConfig.DEBUG) Log.d(TAG, "begin readFileFromPhone....." + fileName);

		File file = new File(context.getFilesDir(), fileName);

		if (file.exists()) {
			FileInputStream inStream = context.openFileInput(file.getName());
			return readStream(inStream);
		}else{
			return null;
		}
	}

}

package com.example.administrator.LookAndLost.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.LookAndLost.App;
import com.example.administrator.LookAndLost.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @ClassName: FileUtil
 * @Description: 文件工具类
 * @author scofieldandroid@gmail.com
 * @date 2013-1-14 上午2:08:42
 * 
 */
public class FileUtil extends BaseFileUtil {
	

	/**
	 * @Title: readContentFromAssets
	 * @Description: 从Assets中获取文件内容
	 * @param context
	 * @param fileName
	 * @return
	 * @author scofieldandroid@gmail.com
	 * @returnType String
	 */
	public static String readContentFromAssets(Context context, String fileName) {

		String content = null;

		InputStream inStream = null;
		try {
			inStream = context.getAssets().open(fileName);
			content = new String(FileUtil.readStream(inStream),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * @Title: readFileFromPhone
	 * @Description: 读取手机自带存储设备上的文件内容
	 * @param fileName
	 * @param context
	 * @return
	 * @throws Exception
	 * @author scofieldandroid@gmail.com
	 * @returnType byte[]
	 */
	public static String readContentFromPhone(String fileName, Context context) throws Exception {

		File file = new File(context.getFilesDir(), fileName);

		if (file.exists()) {
			FileInputStream inStream = context.openFileInput(file.getName());
			return new String(FileUtil.readStream(inStream),"UTF-8");
		} else {
			return null;
		}
	}

	/**
	 * @Title: readFile
	 * @Description: 如果SDCard存在，则在SDCard中读取文件，如果读取不到，则到手机中读取
	 *               如果SDCard不存在，则在手机自带存储中读取文件
	 * @param filename
	 * @param context
	 * @return
	 * @author scofieldandroid@gmail.com
	 * @returnType byte[]
	 */
	public static byte[] readFile(String filename, Context context) {
		try {
			if (isRemovedSDCard) {
				return readFileFromPhone(filename, context);
			} else {
				byte[] byteContent = getFileFromSDCard(filename);
				return byteContent == null ? readFileFromPhone(filename, context) : byteContent;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title: saveFile
	 * @Description: <pre>
	 * 1-如果SDCard存在，则在SDCard中存储文件，如果存储不成功，则在手机自带存储中存储文件。
	 * 2-如果SDCard不存在，则在手机自带存储中存储文件
	 * </pre>
	 * @param fileName
	 * @param byteContent
	 * @param context
	 * @param mode
	 * @throws Exception
	 * @author scofieldandroid@gmail.com
	 * @returnType void
	 */
	public static void saveFile(String fileName, byte[] byteContent, Context context, int mode) {
		try {
			if (isRemovedSDCard) {
				saveToPhone(fileName, byteContent, context, mode);
			} else {
				boolean isSuccess = saveToSDCard(fileName, byteContent);
				if (!isSuccess) {
					saveToPhone(fileName, byteContent, context, mode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取临时文件夹的路径
	 */
	public String getTempFolderPath() {
		return "";
	}

	/**
	 * 根据文件名删除文件
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-8-7 下午2:13:52
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return;
		}
		File file = new File(getFilePath(), fileName);
		if (null != file) {
			file.delete();
		}
	}

	/**
	 * 该文件是否存在
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-8-10 下午2:38:16
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return false;
		}
		File file = new File(getFilePath(), fileName);
		return null != file && file.exists() && file.isFile();
	}

	/**
	 * 获取完整的路径
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-8-10 上午11:27:02
	 * @return
	 */
	public static String getFilePath() {
		// 如果存储卡存在,则使用存储卡.不然就用系统的
		return getBasePath() + File.separator + "filepath";
	}

	/**
	 * 获取存储的根目录
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-8-15 下午2:15:28
	 * @return
	 */
	public static String getBasePath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//去掉包的SDCARD存储路径
			return Environment.getExternalStorageDirectory() + "/xunwuqishi";
		}


		if(App.getAppContext().getCacheDir() == null) {
			if (BuildConfig.DEBUG) Log.d("FileUtil", "cache dir is null");
			return null;
		}


		String path = App.getAppContext().getCacheDir().getPath();
		if (BuildConfig.DEBUG) Log.d("getBasePath", path);
		return path;
	}

	/**
	 * 根据完整路径删除文件
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2013-3-19 下午4:16:53
	 * @param filePath
	 */
	public static void deleteFileWithPath(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return;
		}
		File file = new File(filePath);
		if (null != file) {
			file.delete();
		}
	}

	/**
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2013-3-20 下午4:20:27
	 * @param tempFilePath
	 */
	public static void clearTempRarDir(String tempFilePath) {
		File file = new File(tempFilePath);
		// file.delete();
		deleteDir(file);
	}

	/**
	 * 删除指定文件夹下的所有文件和文件夹
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-8-13 上午9:47:03
	 * @param dirFile
	 */
	private static void deleteDir(File dirFile) {
		if (null == dirFile) {
			return;
		}
		File[] files = dirFile.listFiles();
		if (null == files) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDir(file);
				file.delete();
			} else if (file.isFile()) {
				file.delete();
			}
		}
	}
	
	public static File createFileDir(String dir){
		File file=new File(getBasePath(), dir);
		if (BuildConfig.DEBUG) Log.d("createFileDir", file.getPath());
		try {
			return createFileIfNeed(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static boolean getSDCardState() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static File createSDFile(String path, String filename)
			throws IOException {
		File f = new File(Environment.getExternalStorageDirectory().toString()
				+ "/" + path);
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(f.getPath(), filename);
		if (!f.exists()) {
			f.createNewFile();
		}else{
			f.delete();
			f.createNewFile();
		}
		return f;
	}
	
	/**
	 * 复制ASSETS文件到其他位置
	 * @param context
	 * @param InFileName
	 * @param strOutFileName
	 * @throws IOException
	 */
	public static void copyBigDataToSD(Context context, String InFileName,
			String strOutFileName) throws IOException {
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(strOutFileName);
		myInput = context.getAssets().open(InFileName);
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}
		myOutput.flush();
		myInput.close();
		myOutput.close();
	}

}

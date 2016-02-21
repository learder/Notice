package com.example.administrator.LookAndLost.utils;

import android.content.Context;
import android.util.Log;

import com.example.administrator.LookAndLost.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/20.
 */
public class FileUtils {

//    public static void copyAssetDirToFiles(Context context, String dirname)
//            throws IOException {
//        File dir = new File(context.getFilesDir() + "/" + dirname);
//        dir.mkdir();
//
//        AssetManager assetManager = context.getAssets();
//        String[] children = assetManager.list(dirname);
//        for (String child : children) {
//            child = dirname + '/' + child;
//            String[] grandChildren = assetManager.list(child);
//            if (0 == grandChildren.length)
//                copyAssetFileToFiles(context, child);
//            else
//                copyAssetDirToFiles(context, child);
//        }
//    }

    public static void copy(Context myContext, String ASSETS_NAME,
                            String savePath, String saveName) {
        String filename = savePath + "/" + saveName;

        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists()){
            boolean b=dir.mkdirs();
            if (BuildConfig.DEBUG) Log.d("FileUtils", "b:" + b);
        }


        try {
//            filename= Environment.getExternalStorageDirectory()+"/"+saveName;
            if (!(new File(filename)).exists()) {
                InputStream is = myContext.getResources().getAssets()
                        .open(ASSETS_NAME);
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
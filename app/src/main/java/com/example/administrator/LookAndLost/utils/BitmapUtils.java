package com.example.administrator.LookAndLost.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/2/29.
 */
public class BitmapUtils {

    public static Uri saveImageToGallery(Context context, Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "xunwuqishi/img");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "xunwuqishi.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(file);
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
        return uri;
    }
}

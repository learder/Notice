package com.example.administrator.LookAndLost.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.administrator.LookAndLost.R;

/**
 * Created by 颜厥共 on 2016/2/23.
 * email:644613693@qq.com
 */
public class ShareUtils {

    public static void share(Context context) {
        share(context, context.getString(R.string.share_text));
    }


    public static void shareImage(Context context, Uri uri, String title,String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (uri==null){
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra("sms_body", content);
        }else {
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.action_share)));
    }

}
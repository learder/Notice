package com.example.administrator.LookAndLost.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.entity.ReleaseResultEntity;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.FileUtil;
import com.example.administrator.LookAndLost.utils.TimeUtils;
import com.example.administrator.LookAndLost.utils.network.CommandIdManager;
import com.example.administrator.LookAndLost.utils.network.NetRequest;
import com.example.administrator.LookAndLost.utils.network.ParamManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 颜厥共 on 2016/2/19.
 * email:644613693@qq.com
 */
public class LookAndLostReleaseActivity extends BaseBarActivity {


    @InjectView(R.id.release_add_img_sdv)
    ImageView releaseAddImgSdv;
    @InjectView(R.id.release_type_rg)
    RadioGroup releaseTypeRg;
    @InjectView(R.id.release_title_edt)
    EditText releaseTitleEdt;
    @InjectView(R.id.release_content_edt)
    EditText releaseContentEdt;
    @InjectView(R.id.release_address_edt)
    EditText releaseAddressEdt;
    @InjectView(R.id.release_name_edt)
    EditText releaseNameEdt;
    @InjectView(R.id.release_contect_edt)
    EditText releaseContectEdt;
    @InjectView(R.id.release_reward_edt)
    EditText releaseRewardEdt;
    @InjectView(R.id.release_notes_edt)
    EditText releaseNotesEdt;
    @InjectView(R.id.release_release_btn)
    Button releaseReleaseBtn;

    ProgressBar progressBar;
    private String imageStr;

    private Uri imageUri;
    private Uri zoomUri;


    @Override
    protected int getContentView() {
        return R.layout.activity_look_and_lost_release;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("发布寻物启事");

    }


    @OnClick({R.id.release_release_btn, R.id.release_add_img_sdv})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.release_add_img_sdv) {
            AlertDialog dialog = new AlertDialog.Builder(context).setPositiveButton("相册", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, Constants.RESULT_CODE_PHOTO);

                }
            }).setNeutralButton("相机", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";// temp file
                     imageUri = Uri.parse(IMAGE_FILE_LOCATION);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, Constants.RESULT_CODE_CAMERA);
                }
            }).setCancelable(true).show();
        }
        if (id == R.id.release_release_btn) {
            request();
        }
    }

    private void request() {
        int type_by_id = releaseTypeRg.getCheckedRadioButtonId();
        int type = 0;
        switch (type_by_id) {
            case R.id.release_look_rb:
                type = 1;
                break;
            case R.id.release_lost_rb:
                type = 2;
                break;
        }
        if (type == 0) {
            snackbarShow("请至少选择一种发布类型：“寻物或启示”");
            return;
        }
        String title = releaseTitleEdt.getText().toString();
        if (TextUtils.isEmpty(title)) {
            snackbarShow("请输入标题");
            return;
        }
        String contact = releaseContectEdt.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            snackbarShow("请至少输入一种联系方式，方便别人联系你");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ParamManager.ReleaseEntity.RELEASE_TYPE, type);
            jsonObject.put(ParamManager.ReleaseEntity.TITLE, releaseTitleEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.USER_NAME, releaseNameEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.ADDRESS, releaseAddressEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.CONTACT, releaseContectEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.CONTENT, releaseContentEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.NOTES, releaseNotesEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.REWARD, releaseRewardEdt.getText().toString());
            jsonObject.put(ParamManager.ReleaseEntity.IMGS, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommandIdManager.postRelease(jsonObject, new CallBack(), false);

        if (progressBar == null) {
            progressBar =new ProgressBar(context);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity =Gravity.CENTER;
            progressBar.setVisibility(View.VISIBLE);
            addContentView(progressBar,lp);
        }

    }

    private class CallBack extends NetRequest.ResultCallback<ReleaseResultEntity> {

        @Override
        public void onError(int error, String str) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
                snackbarShow("发布失败:error" + error);
            }
        }

        @Override
        public void onResponse(ReleaseResultEntity response) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
                snackbarShow(TimeUtils.getTimeFromLong(response.getTimeout()));
            }

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case Constants.RESULT_CODE_PHOTO:
                    if (data == null) {
                        snackbarShow("图片获取失败！");
                        return;
                    }
                    Bitmap bm = decodeUriAsBitmap(data.getData());

                    if (bm == null) {
                        snackbarShow("图片获取失败！");
                        return;
                    }
                    if (bm.getHeight() < 240 || bm.getWidth() < 240) {
                        snackbarShow("图片尺寸过小， 请重新上传！");
                        return;
                    }
                    cropImageUri(data.getData());
                    break;

                case Constants.RESULT_CODE_CAMERA:
                    Bitmap bm2 = decodeUriAsBitmap(imageUri);

                    if (bm2 == null)
                        return;
                    if (bm2.getHeight() < 240 || bm2.getWidth() < 240) {
                        snackbarShow("图片尺寸过小， 请重新上传！");
                        return;
                    }
                    cropImageUri(imageUri);
                    break;

                case Constants.RESULT_CODE_ZOOM:
                    if (zoomUri != null) {
                        Bitmap bm3 = decodeUriAsBitmap(zoomUri);
                        if (bm3 == null) {
                            snackbarShow("无法获取图片！");
                            return;
                        }

                        if (bm3.getHeight() < 240 || bm3.getWidth() < 240) {
                            snackbarShow("图片尺寸过小， 请重新上传！");
                            return;
                        }

                        releaseAddImgSdv.setImageBitmap(bm3);

                        if (!FileUtil.getSDCardState()) {
                            snackbarShow("储存卡状态不可用");
                            break;
                        }
                        imageStr = imgToBase64(bm3, 200);
                        File f = null;
                        try {
                            f = FileUtil.createSDFile("/LookAndLost/", "avatar.png");
                            f.createNewFile();
                            compressImage(zoomUri.getPath(),
                                    f.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }

    }


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        if (uri==null){
            return null;
        }
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (Error e) {
            System.gc();
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private void cropImageUri(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // aspect最好不用用1，否则有些手机会不兼容
        intent.putExtra("aspectX", 300);
        intent.putExtra("aspectY", 300);
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);

        // 裁剪时不以黑边填充
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        zoomUri = Uri.parse("file:///sdcard/temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, zoomUri);
		intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, Constants.RESULT_CODE_ZOOM);
    }

    /**
     * 压缩图片并且转换为字符串
     *
     * @return
     */
    public static String imgToBase64(Bitmap bitmap, int size) {
        if (bitmap == null) {
            return "";
        }
        size = 0 == size ? 100 : size;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            int options = 100;
            while (out.toByteArray().length / 1024 > size) {
                options -= 10;// 每次都减少10
                out.reset();// 重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);
            }
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取图片宽 保持宽在700以下
     *
     * @param srcPath
     * @return
     */
    public static void compressImage(String srcPath, String desPath) {

        try {

            // ==================测量
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float ww = 600f;
            float hh = 1000f;
            // 缩放比。
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            bitmap = BitmapRotateUtil.getInstance().checkBitmapAngleToAdjust(
                    srcPath, bitmap);
            // ==================压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 压缩到小于800kb,
            while (baos.toByteArray().length / 1024 > 800) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            // File myCaptureFile = new File( + fileName + ".jpg");
            BufferedOutputStream fbos;
            try {
                fbos = new BufferedOutputStream(new FileOutputStream(desPath));
                fbos.write(baos.toByteArray());
                // fbos = new BufferedOutputStream(baos);
                fbos.flush();
                fbos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }

    public static class BitmapRotateUtil {
        private static BitmapRotateUtil bitmapRotateUtils;

        private BitmapRotateUtil() {
        }

        public static BitmapRotateUtil getInstance() {
            if (null == bitmapRotateUtils) {
                bitmapRotateUtils = new BitmapRotateUtil();
            }
            return bitmapRotateUtils;
        }

        /**
         * 得到 图片旋转 的角度
         *
         * @param filepath
         * @return
         **/
        private int getExifOrientation(String filepath) {
            int degree = 0;
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(filepath);
                if (exif != null) {
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                    if (orientation != -1) {
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                degree = 90;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                degree = 180;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                degree = 270;
                                break;
                        }
                    }
                }
            } catch (IOException ex) {
            }
            return degree;
        }


        /**
         * @param angle  图片发生旋转的角度
         * @param bitmap 要旋转的bitmap
         * @return
         */
        private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
            try {
                if (angle != 0) {  //如果照片出现了 旋转 那么 就更改旋转度数
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
            } catch (Exception e) {
            }
            return bitmap;
        }

        /**
         * 检查bitmap角度发生变换，如果发生变化进行校验，防止图片颠倒
         *
         * @param filePath
         * @param bitmap
         * @return
         */
        public Bitmap checkBitmapAngleToAdjust(String filePath, Bitmap bitmap) {
            int angle = getExifOrientation(filePath);
            return rotateBitmap(angle, bitmap);
        }

    }

    protected void snackbarShow(String str) {
        Snackbar.make(releaseAddImgSdv, str, Snackbar.LENGTH_SHORT).show();
    }

}

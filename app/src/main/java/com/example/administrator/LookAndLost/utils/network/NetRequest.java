package com.example.administrator.LookAndLost.utils.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.LookAndLost.App;
import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.SPUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/2/20.
 */
public class NetRequest {

    RequestQueue requestQueue;

    private static final String URL="www.baidu.com";

    private static String device="";
    private static int versionCode=-1;
    private Gson gson;

    private NetRequest(){
        requestQueue=Volley.newRequestQueue(App.getAppContext());
        gson=new Gson();

    }

    public interface NetRequestCallBack<T>{

        void onSuccess(T t);
        void onFail(int error,String str);
        void onEmpty();

    }

    public synchronized void request(int commandId,JSONObject param, NetRequestCallBack callBack,boolean cache){
        JSONObject newparam=getCompleteParam(commandId,param);
        if (BuildConfig.DEBUG) Log.d("param", newparam.toString());

        if (cache){
            //数据库
        }else {
            netRequest(newparam,callBack);
        }

    }



    /**
     * 网络请求
     * @param params 参数
     */
    private void netRequest(JSONObject params, final NetRequestCallBack netRequestCallBack) {

        // Volley请求
        StringRequest stringRequest=new StringRequest(URL + params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)){
                    netRequestCallBack.onFail(ResultCode.RESULT_CLIENT_UNKNOW.getCode(),ResultCode.RESULT_CLIENT_UNKNOW.getString());
                }
                try {
                    JSONObject json=new JSONObject(response);
                    int resultCode=json.optInt("resultCode");
                    if (resultCode==ResultCode.RESULT_SERVICE_SUCCESS.getCode()){
                        String str=json.getString("data");
                        netRequestCallBack.onSuccess(str);
                    }else {
                        String resultString=json.optString("resultString");
                        netRequestCallBack.onFail(resultCode,resultString);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    netRequestCallBack.onFail(ResultCode.RESULT_CLIENT_JSONERROR.getCode(),ResultCode.RESULT_CLIENT_JSONERROR.getString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netRequestCallBack.onFail(ResultCode.RESULT_CLIENT_VOLLEY_ERROR.getCode(),ResultCode.RESULT_CLIENT_VOLLEY_ERROR.getString());
            }
        });

        requestQueue.add(stringRequest);
    }

    /**
     * 整合完整的参数
     * @param jsonObject
     * @return
     */
    private JSONObject getCompleteParam(int commandId,JSONObject jsonObject){
        if (jsonObject==null){
            jsonObject=new JSONObject();
        }
        try {
            jsonObject.put("commandId",commandId);
            jsonObject.put("deviceId",getDeviceId());
            jsonObject.put("clientVersion",getClientVersionCode());
            jsonObject.put("city", SPUtils.get4Sp(App.getAppContext(),Constants.KEY_CITY,Constants.DEFAULT_CITY));
            jsonObject.put("userId",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取设备ID
     *
     * @return
     */
    private static String getDeviceId() {
        if (TextUtils.isEmpty(device)){
            TelephonyManager manager = (TelephonyManager) App.getAppContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            device=manager.getDeviceId();
        }
        return device;
    }

    /**
     * 获取客户端版本号
     *
     * @return 客户端版本号
     */
    private static int getClientVersionCode() {
        if (versionCode==-1){
            PackageManager pm = (PackageManager) App.getAppContext().getApplicationContext().getPackageManager();

            try {
                PackageInfo pi = pm.getPackageInfo(App.getAppContext().getApplicationContext().getPackageName(), 0);
                versionCode=pi.versionCode;

            } catch (Exception ex) {
                return -1;
            }
        }
        return versionCode;

    }


}

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
import com.example.administrator.LookAndLost.TestSource;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/2/20.
 * 网络请求
 */
public class NetRequest {

    RequestQueue requestQueue;

    private static final String URL="www.baidu.com";

    private static String device="";
    private static int versionCode=-1;
    private Gson gson;
    private static NetRequest instance;

    private NetRequest(){
        requestQueue=Volley.newRequestQueue(App.getAppContext());
        gson=new Gson();
    }

    public static NetRequest getInstance(){
        if (instance==null){
            instance=new NetRequest();
        }
        return instance;
    }

    public synchronized void request(int commandId,JSONObject param,ResultCallback callback, boolean cache){
        JSONObject newparam=getCompleteParam(commandId,param);
        if (BuildConfig.DEBUG) Log.d("param", newparam.toString());

        if (cache){
            //数据库
        }else {
//            netRequest(newparam,callback);
            TestSource.getInstance().netRequest(commandId,callback);
        }

    }



    /**
     * 网络请求
     * @param params 参数
     */
    private void netRequest(JSONObject params, final ResultCallback callback) {

        // Volley请求
        StringRequest stringRequest=new StringRequest(URL + params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (BuildConfig.DEBUG) Log.d("NetRequest", response);
                if (TextUtils.isEmpty(response)){
                    callback.onError(ResultCode.RESULT_CLIENT_UNKNOW.getCode(),ResultCode.RESULT_CLIENT_UNKNOW.getString());
                }
                try {
                    JSONObject json=new JSONObject(response);
                    int resultCode=json.optInt(ParamManager.Common.RESULT_CODE);
                    if (resultCode==ResultCode.RESULT_SERVICE_SUCCESS.getCode()||resultCode==ResultCode.RESULT_CLIENT_SUCCESS.getCode()){
                        String str=json.getString(ParamManager.Common.DATA);
                        if (callback.mType == String.class)
                        {
                            callback.onResponse(str);
                        } else
                        {
                            Object o = gson.fromJson(str, callback.mType);
                            callback.onResponse(o);
                        }
                    }else {
                        String resultString=json.optString(ParamManager.Common.RESULT_STRING);
                        callback.onError(resultCode,resultString);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(ResultCode.RESULT_CLIENT_JSONERROR.getCode(),ResultCode.RESULT_CLIENT_JSONERROR.getString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
                if (BuildConfig.DEBUG) Log.d("NetRequest", "错误!!"+" callBack-->"+callback.toString());
                callback.onError(ResultCode.RESULT_CLIENT_VOLLEY_ERROR.getCode(),ResultCode.RESULT_CLIENT_VOLLEY_ERROR.getString());
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
            jsonObject.put(ParamManager.Common.COMMAND_ID,commandId);
            jsonObject.put(ParamManager.Common.DEVICE_ID,getDeviceId());
            jsonObject.put(ParamManager.Common.CLIENT_VERSION,getClientVersionCode());
            jsonObject.put(ParamManager.Common.CITY, SPUtils.get4Sp(App.getAppContext(),Constants.KEY_CITY,Constants.DEFAULT_CITY));
            jsonObject.put(ParamManager.Common.USER_ID,0);
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

    public static abstract class ResultCallback<T>
    {
        public Type mType;

        public ResultCallback()
        {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(int error,String str);

        public abstract void onResponse(T response);
    }


}

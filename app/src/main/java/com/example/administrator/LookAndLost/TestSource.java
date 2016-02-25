package com.example.administrator.LookAndLost;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.LookAndLost.utils.network.CommandIdManager;
import com.example.administrator.LookAndLost.utils.network.NetRequest;
import com.example.administrator.LookAndLost.utils.network.ParamManager;
import com.example.administrator.LookAndLost.utils.network.ResultCode;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 颜厥共 on 2016/2/23.
 * email:644613693@qq.com
 */
public class TestSource {

    private static TestSource instance;

    private Gson gson;

    public TestSource() {
        gson=new Gson();
    }

    public static TestSource getInstance(){
        if (instance==null){
            instance=new TestSource();
        }
        return instance;
    }


    public void netRequest(int commandId,NetRequest.ResultCallback callback ){
        String response=getTestSource(commandId);
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

    private String getTestSource(int commandId){
        JSONObject reult=new JSONObject();
        try {
            reult.put("resultCode", ResultCode.RESULT_CLIENT_SUCCESS.getCode());
            reult.put("resultString",ResultCode.RESULT_CLIENT_SUCCESS.getString());
            switch (commandId){
                case CommandIdManager.COMMAND_ID_LOOK_LIST:
                    JSONArray looks=new JSONArray();
                    JSONObject look;
                    for (int i=0;i<3;i++){
                        look=new JSONObject();
                        look.put(ParamManager.LookAndLostEntity.TITLE,"寻物启事，标题"+i);
                        look.put(ParamManager.LookAndLostEntity.USER_NAME,"用户名："+i);
                        look.put(ParamManager.LookAndLostEntity.ADDRESS,"地址：文一西路未来科技城海创园"+i+"号");
                        look.put(ParamManager.LookAndLostEntity.CONTACT,"1876843517");
                        look.put(ParamManager.LookAndLostEntity.CONTENT,"大年初一掉了钥匙！！！！！！！！！！！！狗日啊！！！！！！！");
                        look.put(ParamManager.LookAndLostEntity.EVENT_ID,i);
                        look.put(ParamManager.LookAndLostEntity.EVENT_TYPE,"钥匙");
                        look.put(ParamManager.LookAndLostEntity.IMGS,new JSONArray());
                        look.put(ParamManager.LookAndLostEntity.NOTES,"备注");
                        look.put(ParamManager.LookAndLostEntity.REWARD,"5块钱赏你");
                        look.put(ParamManager.LookAndLostEntity.TIMEOUT,SystemClock.currentThreadTimeMillis());
                        looks.put(look);
                    }
                    reult.put(ParamManager.Common.DATA,looks);

                    break;
                case CommandIdManager.COMMAND_ID_LOST_LIST:
                    JSONArray losts=new JSONArray();
                    JSONObject lost;
                    for (int i=0;i<100;i++){
                        lost=new JSONObject();
                        lost.put(ParamManager.LookAndLostEntity.TITLE,"寻物启事，标题"+i);
                        lost.put(ParamManager.LookAndLostEntity.USER_NAME,"用户名："+i);
                        lost.put(ParamManager.LookAndLostEntity.ADDRESS,"地址：文一西路未来科技城海创园"+i+"号");
                        lost.put(ParamManager.LookAndLostEntity.CONTACT,"1876843517");
                        lost.put(ParamManager.LookAndLostEntity.CONTENT,"大年初一掉了钥匙！！！！！！！！！！！！狗日啊！！！！！！！");
                        lost.put(ParamManager.LookAndLostEntity.EVENT_ID,i);
                        lost.put(ParamManager.LookAndLostEntity.EVENT_TYPE,"钥匙");
                        lost.put(ParamManager.LookAndLostEntity.IMGS,new JSONArray());
                        lost.put(ParamManager.LookAndLostEntity.NOTES,"备注");
                        lost.put(ParamManager.LookAndLostEntity.REWARD,"5块钱赏你");
                        lost.put(ParamManager.LookAndLostEntity.TIMEOUT,SystemClock.currentThreadTimeMillis());
                        losts.put(lost);
                    }
                    reult.put(ParamManager.Common.DATA,losts);
                    break;
                case CommandIdManager.COMMAND_ID_RELEASE:

                    break;

                default:
                    reult.put("resultCode", ResultCode.RESULT_CLIENT_COMMAND_ID_ERROR.getCode());
                    reult.put("resultString",ResultCode.RESULT_CLIENT_COMMAND_ID_ERROR.getString());
                    break;

            }
//            return  new Gson().toJson(reult);
            return reult.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return reult.toString();
        }

    }


}

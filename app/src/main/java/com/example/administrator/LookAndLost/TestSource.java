package com.example.administrator.LookAndLost;

import com.example.administrator.LookAndLost.utils.network.CommandIdManager;
import com.example.administrator.LookAndLost.utils.network.ResultCode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 颜厥共 on 2016/2/23.
 * email:644613693@qq.com
 */
public class TestSource {

    public String getTestSource(int commandId){
        JSONObject reult=new JSONObject();
        try {
            reult.put("resultCode", ResultCode.RESULT_CLIENT_SUCCESS.getCode());
            reult.put("resultString",ResultCode.RESULT_CLIENT_SUCCESS.getString());
            switch (commandId){
                case CommandIdManager.COMMAND_ID_LOOK_LIST:

                    break;
                case CommandIdManager.COMMAND_ID_LOST_LIST:

                    break;
                case CommandIdManager.COMMAND_ID_RELEASE:

                    break;

                default:
                    reult.put("resultCode", ResultCode.RESULT_CLIENT_COMMAND_ID_ERROR.getCode());
                    reult.put("resultString",ResultCode.RESULT_CLIENT_COMMAND_ID_ERROR.getString());
                    break;

            }
            return reult.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return reult.toString();
        }

    }


}

package com.example.administrator.LookAndLost.utils.network;

import com.example.administrator.LookAndLost.utils.network.NetRequest;

import org.json.JSONObject;

/**
 * Created by 颜厥共 on 2016/2/22.
 * email:644613693@qq.com
 * 服务器接口管理
 */
public class CommandIdManager {
    public static final int COMMAND_ID_LOST_LIST=100;
    public static final int COMMAND_ID_LOOK_LIST=101;
    public static final int COMMAND_ID_RELEASE=102;

    public static void getLostList(JSONObject param, NetRequest.ResultCallback callback, boolean cache){
        NetRequest.getInstance().request(COMMAND_ID_LOST_LIST,param,callback,cache);
    }

    public static void getLookList(JSONObject param, NetRequest.ResultCallback callback, boolean cache){
        NetRequest.getInstance().request(COMMAND_ID_LOOK_LIST,param,callback,cache);
    }

    public static void postRelease(JSONObject param, NetRequest.ResultCallback callback, boolean cache){
        NetRequest.getInstance().request(COMMAND_ID_RELEASE,param,callback,cache);
    }


}

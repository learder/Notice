package com.example.administrator.LookAndLost.utils.network;

/**
 * Created by Administrator on 2016/2/20.
 */
public enum ResultCode {

    RESULT_SERVICE_SUCCESS(10000){
        public String getString(){
            return "服务器请求成功";
        }
    },

    RESULT_CLIENT_SUCCESS(2000){
      public String getString(){
          return "客户端测试数据请求成功";
      }
    },

    RESULT_CLIENT_UNKNOW(20001){
        public String getString(){
            return "服务器返回的数据为空";
        }
    },

    RESULT_CLIENT_NETWORK_ERROR(20002){
        public String getString(){
            return "网络连接错误";
        }
    },

    RESULT_CLIENT_JSONERROR(20003){
        public String getString(){
            return "JSON转换错误";
        }
    },

    RESULT_CLIENT_VOLLEY_ERROR(20004){
        public String getString(){
            return "Volley错误";
        }
    },

    RESULT_CLIENT_COMMAND_ID_ERROR(20005){
        public String getString(){
            return "测试数据没有这个接口数据";
        }
    };

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public abstract String getString();
}
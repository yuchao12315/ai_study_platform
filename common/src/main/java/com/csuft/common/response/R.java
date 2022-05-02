package com.csuft.common.response;

import lombok.Data;

/**
 * 返回的类
 * 数据有：
 * 是否成功： success {true/false} 类型 boolean
 * 状态码：code {20000/40000} int
 * 消息：msg 对code 的说明 比如说操作成功/失败,登录成功  String
 * 返回数据：data 类型 object
 */

@Data
public class R {

    public static final int CODE_SUCCESS = 20000;
    public static final int CODE_FAILED= 40000;
    public static final int CODE_NOT_LOGIN= 40001;

    //是否成功
    private boolean success;
    //状态码
    private int code;
    //消息
    private String msg;
    //返回数据
    private Object data;

    public R setData(Object data) {
        this.data = data;
        return  this;
    }

    //提供一些静态的方法，可以快速的创建返回对象
    public static R SUCCESS(String msg) {
        R r = new R();
        r.code = CODE_SUCCESS;
        r.msg = msg;
        r.success = true;
        return r;
    }

    public static R SUCCESS(String msg, Object data) {
        R success = SUCCESS(msg);
        success.data = data;
        return success;

    }    public static R NOT_LOGIN() {
        R failed = FAILED("账号未登录");
        failed.data = CODE_NOT_LOGIN;
        return failed;
    }

    public static R FAILED(String msg) {
        R r = new R();
        r.code = CODE_FAILED;
        r.msg = msg;
        r.success = false;
        return r;
    }

    public static R FAILED(String msg, Object data) {
        R failed = FAILED(msg);
        failed.data = data;
        return failed;
    }


}
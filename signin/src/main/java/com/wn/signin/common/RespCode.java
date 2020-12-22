package com.wn.signin.common;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 返回码
 * @date 2020-12-22-11-05
 **/
public enum RespCode {

    SUCCESS(200,"成功"),
    FAIL(101,"失败");

    private int code ;
    private String desc;

    RespCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

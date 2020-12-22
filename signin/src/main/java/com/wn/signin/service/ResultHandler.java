package com.wn.signin.service;

import com.wn.signin.common.RespCode;
import com.wn.signin.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 返回结果处理
 * @date 2020-12-22-11-09
 **/
@Service
public class ResultHandler {

    @Autowired
    RespResult respResult;

    public RespResult handleResult(RespCode respCode,String msg,Object data){
        respResult.setCode(respCode.getCode());
        respResult.setMsg(msg);
        respResult.setData(data);
        return respResult;
    }

    public RespResult handleResult(RespCode respCode,Object data){
        respResult.setData(data);
        respResult.setCode(respCode.getCode());
        respResult.setMsg(respCode.getDesc());
        return respResult;
    }



}

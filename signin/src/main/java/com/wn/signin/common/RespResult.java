package com.wn.signin.common;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 返回结果类
 * @date 2020-12-22-11-08
 **/
@Data
@Component
public class RespResult {

    private int code;
    private String msg;
    private Object data;

}

package com.ningmeng.framework.exception;

import com.ningmeng.framework.model.response.ResultCode;

/**
 * Created by Lenovo on 2020/2/12.
 * RuntimeException:不可预知异常
 * 自定义的一个异常处理
 */
public class CustomException extends RuntimeException {
    private ResultCode resultCode;

    public CustomException( ResultCode resultCode) {
        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return  this.resultCode;
    }
}

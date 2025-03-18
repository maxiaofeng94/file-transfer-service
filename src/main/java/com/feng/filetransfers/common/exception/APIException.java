package com.feng.filetransfers.common.exception;

import com.feng.filetransfers.model.enums.ResultCode;
import lombok.Getter;

@Getter
public class APIException extends RuntimeException {
    private final int code;
    private final String msg;

    public APIException() {
        this(ResultCode.FAILED.getCode(), "接口错误");
    }

    public APIException(String msg) {
        this(ResultCode.FAILED.getCode(), msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}

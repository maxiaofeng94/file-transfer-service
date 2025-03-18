package com.feng.filetransfers.model.vo;

import com.feng.filetransfers.model.enums.ResultCode;
import lombok.Getter;

/**
 * 接口统一响应结果实体类
 * @param <T> 响应结果数据的泛型
 */
@Getter
public class ResultVO<T> {
    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应状态信息
     */
    private final String message;

    /**
     * 响应数据
     */
    private final T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode code) {
        this(code, null);
    }

    public ResultVO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }
}

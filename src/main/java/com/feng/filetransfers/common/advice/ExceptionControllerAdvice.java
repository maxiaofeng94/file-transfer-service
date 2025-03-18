package com.feng.filetransfers.common.advice;

import com.feng.filetransfers.common.exception.APIException;
import com.feng.filetransfers.model.enums.ResultCode;
import com.feng.filetransfers.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * 全局异常拦截处理
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    /**
     * 接口参数验证失败异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("{}：", ResultCode.VALIDATE_FAILED.getMessage(), ex);
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }

    /**
     * 接口参数验证失败异常处理
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<?> HandlerMethodValidationExceptionHandler(HandlerMethodValidationException ex) {
        log.error("{}：", ResultCode.VALIDATE_FAILED.getMessage(), ex);
        // 提取错误提示信息进行返回
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, ex.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 自定义的接口异常处理
     */
    @ExceptionHandler(APIException.class)
    public ResultVO<?> APIExceptionHandler(APIException ex) {
        log.error("接口自定义异常：", ex);
        return new ResultVO<>(ResultCode.FAILED, ex.getMsg());
    }

    /**
     * 系统预期之外的异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> handleUnexpectedServer(Exception ex) {
        log.error("系统异常：", ex);
        return new ResultVO<>(ResultCode.ERROR, ex.getMessage());
    }

    /**
     * 所有异常的拦截处理
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> exception(Throwable ex) {
        log.error("系统异常：", ex);
        return new ResultVO<>(ResultCode.ERROR, ex.getMessage());
    }
}

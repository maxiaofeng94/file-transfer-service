package com.feng.filetransfers.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否绕过统一响应注解
 * 此注解作用于RestController类的函数上
 * 当使用此注解，函数的返回结果数据将不会被统一响应处理捕获，不会生成统一的响应数据结构
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotResponseBody {
}

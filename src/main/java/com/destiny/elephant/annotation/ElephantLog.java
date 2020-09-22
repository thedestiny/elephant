package com.destiny.elephant.annotation;

import java.lang.annotation.*;

/**
 * 类SysLog的功能描述:
 * 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElephantLog {
    String value() default "";
}

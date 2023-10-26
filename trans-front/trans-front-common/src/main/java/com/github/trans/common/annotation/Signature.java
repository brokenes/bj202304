package com.github.trans.common.annotation;

import java.lang.annotation.*;

/**
 * 此注解用于验证对象字段属性是否需要
 * 通过md5加密，如果加上了注解required=true,
 * 认为是必选要加密，如果设置为false，则不需要
 * 加密，但是参数不能为空
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Signature {

    /**是否需要加密，默认true加密**/
    boolean required() default true;

    /**字段描述**/
    String desc() default "";

}

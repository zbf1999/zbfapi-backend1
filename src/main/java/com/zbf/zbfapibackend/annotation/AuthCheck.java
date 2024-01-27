package com.zbf.zbfapibackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.MODULE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 有任何一个角色
     */
    String[] anyRole() default "";

    /**
     *必须有某个角色
     */
    String mustRole() default "";
}

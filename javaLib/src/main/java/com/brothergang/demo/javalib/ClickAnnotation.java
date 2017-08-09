package com.brothergang.demo.javalib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by brothergang on 2017/8/7.
 */


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface ClickAnnotation {
    int id();
    String toast();
}

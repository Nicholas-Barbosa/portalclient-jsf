package com.farawaybr.portal.service.states;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@Retention(RUNTIME)
@Target({ TYPE, ElementType.METHOD })
@InterceptorBinding
public @interface StatesCache {

}

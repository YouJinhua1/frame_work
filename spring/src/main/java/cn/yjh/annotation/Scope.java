package cn.yjh.annotation;

import cn.yjh.beans.support.ScopeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    public ScopeType value() default ScopeType.SINGLETON;
}


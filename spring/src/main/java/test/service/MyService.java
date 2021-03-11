package test.service;

import cn.yjh.annotation.AliasFor;
import cn.yjh.annotation.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-03 14:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface MyService {

    @AliasFor(value = "beanName",annotation = Component.class)
    String value() default "";

    @AliasFor(value = "beanName",annotation = Component.class)
    String local() default "";
    @AliasFor(value = "value",annotation = Component.class)
    String test() default "";

    @AliasFor("value")
    String xx() default "";


}

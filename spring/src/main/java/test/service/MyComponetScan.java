package test.service;

import cn.yjh.annotation.AliasFor;
import cn.yjh.annotation.ComponentScan;
import test.entity.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-09 11:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan
public @interface MyComponetScan {
    @AliasFor(value = "basePackages",annotation = ComponentScan.class)
    String[] value() default {};

    @AliasFor(value = "value",annotation = ComponentScan.class)
    String[] basePackages() default {"uuu"};

    @AliasFor(annotation=ComponentScan.class)
    Class<?>[] basePackageClasses() default {};

    @AliasFor(value="basePackageClasses",annotation=ComponentScan.class)
    Class<?>[] basePackageClassesw() default {};


}

package cn.yjh.annotation;

import cn.yjh.beans.support.ScopeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 16:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ComponentScan {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};



//    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;
//
//    Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;
//
//    ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;
//
//    String resourcePattern() default "**/*.class";
//
//    boolean useDefaultFilters() default true;
//
//    ComponentScan.Filter[] includeFilters() default {};
//
//    ComponentScan.Filter[] excludeFilters() default {};
//
//    boolean lazyInit() default false;
//
//    @Retention(RetentionPolicy.RUNTIME)
//    @Target({})
//    public @interface Filter {
//        FilterType type() default FilterType.ANNOTATION;
//
//        @AliasFor("classes")
//        Class<?>[] value() default {};
//
//        @AliasFor("value")
//        Class<?>[] classes() default {};
//
//        String[] pattern() default {};
//    }
}

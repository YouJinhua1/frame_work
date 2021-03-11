package cn.yjh.annotation.support;

import cn.yjh.annotation.Component;
import cn.yjh.annotation.Service;
import test.service.UserService;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-03-06 13:16
 */
public class AnnotationElementUtils {

    public static void main(String[] args) throws Exception {
        Class<UserService> classz = UserService.class;
        long l = System.currentTimeMillis();
        Component annotation = findAnnotation(classz, Component.class, null);
        System.out.println(annotation);
        System.out.println("查找耗时：" + (System.currentTimeMillis() - l) + "ms,查找结果为：" + annotation);
    }

    private static <T extends Annotation> T findAnnotationOnClass(Class<?> clazz, Class<T> targetType,Builder builder) {
        T t = null;
        boolean isFound = false;
        Annotation[] annotations = clazz.getAnnotations();
        clazz.getAnnotation()
        for (Annotation annotation : annotations) {

            Class<? extends Annotation> sourceType = annotation.annotationType();
            String annotationName = sourceType.getName();
            if (!annotationName.startsWith("java") && !annotationName.startsWith("javax")) {
                thisNode = (annotation, node, type);
                if (sourceType == targetType) {
                    isFound = true;
                    t = (T) annotation;
                    System.out.println(t  +"======"+ annotation);
                } else {
                    findAnnotation(sourceType,targetType,null);
                }
            }
            if (isFound) break;
        }
        return t;
    }

    private static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> targetType,Builder builder) {
        T t = null;
        boolean isFound = false;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> sourceType = annotation.annotationType();
            String annotationName = sourceType.getName();
            if (!annotationName.startsWith("java") && !annotationName.startsWith("javax")) {
                thisNode = (annotation, node, type);
                if (sourceType == targetType) {
                    isFound = true;
                    t = (T) annotation;
                    System.out.println(t  +"======"+ annotation);
                } else {
                    findAnnotation(sourceType,targetType,null);
                }
            }
            if (isFound) break;
        }
        return t;
    }


    public static boolean isAnnotationPresentOnClass(Class<?> clazz, Class<? extends Annotation> annotationType) {
        boolean result = false;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getName();
            if (!annotationName.startsWith("java") && !annotationName.startsWith("javax")) {
                if (annotation.annotationType() == annotationType) {
                    result = true;
                } else {
                    result = isAnnotationPresent(annotation.annotationType(), annotationType);
                }
            }
            if (result) break;
        }
        return result;
    }

    private static boolean isAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotationType) {
        boolean result = false;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getName();
            if (!annotationName.startsWith("java") && !annotationName.startsWith("javax")) {
                if (annotation.annotationType() == annotationType) {
                    result = true;
                } else {
                    result = isAnnotationPresent(annotation.annotationType(), annotationType);
                }
            }
            if (result) break;
        }
        return result;
    }

    class AnnotationMate{
        List<Map<String,String>> tree = new ArrayList<>();

    }


    interface Builder<T extends Annotation>{
       T build();
    }
}

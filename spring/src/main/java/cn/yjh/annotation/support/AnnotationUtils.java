package cn.yjh.annotation.support;

import cn.yjh.annotation.AliasFor;
import cn.yjh.annotation.Component;
import cn.yjh.annotation.ComponentScan;
import cn.yjh.annotation.Service;
import test.service.MyComponetScan;
import test.service.MyService;
import test.service.UserService;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-03 13:59
 */
public class AnnotationUtils {

    public static void main(String[] args) throws Exception {
        Class<UserService> classz = UserService.class;
        long l = System.currentTimeMillis();
        Service annotation = findAnotationOnClass(classz, Service.class);
        System.out.println(annotation);
        System.out.println("查找耗时：" + (System.currentTimeMillis() - l) + "ms,查找结果为：" + annotation);
    }

    /**
     * 判断某个自定义注解是否出现在某个类上
     *
     * @param clazz          待判断的类
     * @param annotationType 自定义注解的类型
     * @return
     */
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

    /**
     * 判断某个自定义注解是否出现在某个方法上
     * @param method
     * @param annotationType
     * @return
     */
    public static boolean isAnnotationPresentOnMethod(Method method, Class<? extends Annotation> annotationType) {
        boolean result = false;
        Annotation[] annotations = method.getAnnotations();
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

    /**
     * 判断某个自定义注解是否出现在某个属性上
     * @param field
     * @param annotationType
     * @return
     */
    public static boolean isAnnotationPresentOnField(Field field, Class<? extends Annotation> annotationType) {
        boolean result = false;
        Annotation[] annotations = field.getAnnotations();
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

    public static <T extends Annotation> T findAnotationOnClass(Class<?> clazz, Class<T> annotationType) throws Exception {
        T t = null;
        boolean isFound = false;
        AnnotationDefinition anotation = findAnotation(clazz, annotationType, isFound, null);
        if (anotation != null) {
            t = (T) anotation.thisAnnotation;
            Map annotationAttributeMap = getAnnotationAttributeMap(t);
            Set<String> keys = annotationAttributeMap.keySet();
            for (String key : keys) {
                setAnnotationAttribute(t, key, anotation.attributeMap.get(key).attributeValue);
            }
        }
        return t;
    }

    /**
     * 获取某个类上的自定义注解
     *
     * @param clazz          待判断的类
     * @param annotationType 自定义注解的类型
     * @return
     */
    private static <T extends Annotation> AnnotationDefinition findAnotation(Class<?> clazz, Class<T> annotationType, boolean isFound, AnnotationDefinition node) throws Exception {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            String annotationClassName = type.getName();
            AnnotationDefinition thisNode = null;
            if (!annotationClassName.startsWith("java") && !annotationClassName.startsWith("javax")) {
                thisNode = createAnnotationDefinition(annotation, node, type);
                // annotation.annotationType()
                if (annotation.annotationType() == annotationType) {
                    isFound = true;
                    node = thisNode;
                } else {
                    node = findAnotation(annotation.annotationType(), annotationType, isFound, thisNode);
                }
            }
            if (isFound) break;
        }
        return node;
    }

    private static AnnotationDefinition createAnnotationDefinition(Annotation an, AnnotationDefinition node, Class<? extends Annotation> type) throws Exception {
        AnnotationDefinition annotationDefinition = new AnnotationDefinition();
        Map<String, AliasRegister> aliasRegisterMap = annotationDefinition.aliasRegisterMap;
        Map<String, AttributeMeta> attributeMap = annotationDefinition.attributeMap;
        annotationDefinition.thisAnnotation = an;
        String anClassName = type.getName();
        annotationDefinition.annotationType = type;
        Class<?> anClazz = Class.forName(anClassName);
        Map<String, Object> map = getAnnotationAttributeMap(an);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String attributeName = entry.getKey();
            Object attributeValue = entry.getValue();
            //System.out.println(type.getName() + "{ " + attributeName + " : " + attributeValue + " }");
            AttributeMeta attributeMeta = new AttributeMeta();
            attributeMeta.attributeName = attributeName;
            attributeMeta.attributeValue = attributeValue;
            Method method = anClazz.getDeclaredMethod(attributeName);
            Object defaultValue = method.getDefaultValue();
            if (!isEquals(defaultValue, attributeValue)) {
                attributeMeta.IS_DEFAULT_VALUE = false;
            }
            String BASE_KEY = type.getName() + "_" + attributeName;
            if (node != null && node.aliasRegisterMap.containsKey(BASE_KEY)) {
                //System.out.println(type + " 的属性：" + attributeName + " 的取值为：" + node.attributeMap.get(attributeName).attributeValue + " ，来自：" + node.annotationType + "  的  " + node.attributeMap.get(attributeName).attributeName);
                attributeMeta.attributeValue = node.aliasRegisterMap.get(BASE_KEY).attributeValue;
            }
            if (method.isAnnotationPresent(AliasFor.class)) {
                AliasFor aliasFor = method.getAnnotation(AliasFor.class);
                attributeMeta.aliasForAttribute = aliasFor.attribute();
                attributeMeta.aliasForValue = aliasFor.value();
                attributeMeta.annotation = aliasFor.annotation();
                doRegisterAlias(aliasFor, attributeMeta, aliasRegisterMap);
            }
            attributeMap.put(attributeName, attributeMeta);
        }
        Set<Map.Entry<String, AliasRegister>> entries1 = aliasRegisterMap.entrySet();
        for (Map.Entry<String, AliasRegister> entry : entries1) {
            String key = entry.getKey();
            AliasRegister aliasRegister = entry.getValue();
            AttributeMeta attributeMeta = attributeMap.get(key);
            if (aliasRegister.IS_REGIST_ITSELE) {
                if (!attributeMap.get(key).IS_DEFAULT_VALUE) {
                    if (!isEquals(aliasRegister.attributeValue, attributeMeta.attributeValue)) {
                        if (attributeMeta.attributeValue.getClass().isArray()) {
                            Object[] arr1 = (Object[]) attributeMeta.attributeValue;
                            Object[] arr2 = (Object[]) aliasRegister.attributeValue;
                            throw new Exception("The attribute: { " + attributeMeta.attributeName + " : " + Arrays.toString(arr1) + " } is different from  attribute { " + aliasRegister.attributeName + " : " + Arrays.toString(arr2) + " } !");
                        } else {
                            throw new Exception("The attribute: { " + attributeMeta.attributeName + " : " + attributeMeta.attributeValue + " } is different from  attribute { " + aliasRegister.attributeName + " : " + aliasRegister.attributeValue + " } !");

                        }
                    }
                }
            }
        }
        if (node != null) {
            node.parentList.add(annotationDefinition);
        }
        return annotationDefinition;
    }

    private static void doRegisterAlias(AliasFor aliasFor, AttributeMeta attributeMeta, Map<String, AliasRegister> aliasRegisterMap) throws Exception {
        Class<? extends Annotation> annotation = aliasFor.annotation();
        String excludeType = "java.lang.annotation.Annotation";
        String aliasValue = aliasFor.value();
        String attribute = aliasFor.attribute();
        String BASE_KEY = "";
        boolean is_regist_itself = true;
        if (!excludeType.equals(annotation.getName())) {
            is_regist_itself = false;
            BASE_KEY = annotation.getName();
        }
        if (!"".equals(aliasValue)) {
            registAlias(attributeMeta, aliasRegisterMap, aliasValue, BASE_KEY, is_regist_itself);
        }
        if (!"".equals(attribute)) {
            registAlias(attributeMeta, aliasRegisterMap, attribute, BASE_KEY, is_regist_itself);
        }
        if ("".equals(attribute) && "".equals(aliasValue)) {
            registAlias(attributeMeta, aliasRegisterMap, attributeMeta.attributeName, BASE_KEY, is_regist_itself);
        }
    }

    private static void registAlias(AttributeMeta attributeMeta, Map<String, AliasRegister> aliasRegisterMap, String key, String BASE_KEY, boolean is_regist_itself) throws Exception {
        String aliasKey = BASE_KEY;
        if (is_regist_itself) {
            aliasKey += key;
        } else {
            aliasKey += "_" + key;
        }
        AliasRegister aliasRegister = new AliasRegister();
        aliasRegister.IS_REGIST_ITSELE = is_regist_itself;
        aliasRegister.attributeName = attributeMeta.attributeName;
        aliasRegister.attributeValue = attributeMeta.attributeValue;
        if (!aliasRegisterMap.containsKey(aliasKey)) {
            aliasRegisterMap.put(aliasKey, aliasRegister);
        } else {
            Object o1 = aliasRegisterMap.get(aliasKey).attributeValue;
            Object o2 = attributeMeta.attributeValue;
            if (!isEquals(o1, o2)) {
                if (o1.getClass().isArray()) {
                    Object[] arr1 = (Object[]) o1;
                    Object[] arr2 = (Object[]) o2;
                    throw new Exception("The attribute: { " + attributeMeta.attributeName + " : " + Arrays.toString(arr1) + " } is different from  attribute { " + aliasRegister.attributeName + " : " + Arrays.toString(arr2) + " } !");
                } else {
                    throw new Exception("The attribute: { " + attributeMeta.attributeName + " : " + o2 + " } is different from  attribute { " + aliasRegisterMap.get(aliasKey).attributeName + " : " + o1 + " } !");
                }
            }
            aliasRegisterMap.put(aliasKey, aliasRegister);
        }
    }

    /**
     * 给某个注解的某个属性赋值
     *
     * @param an
     * @param attributeKey
     * @param attributeValue
     * @throws Exception
     */
    private static void setAnnotationAttribute(Annotation an, String attributeKey, Object attributeValue) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(an);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field handlerField = handler.getClass().getDeclaredField("memberValues");
        // 因为这个字段是 private final 修饰，所以要打开权限
        handlerField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) handlerField.get(handler);
        // 修改对应属性的值
        memberValues.put(attributeKey, attributeValue);
    }

    /**
     * 给某个注解的某个属性赋值
     *
     * @param an
     * @param map
     * @throws Exception
     */
    private static void setAnnotationAttribute(Annotation an, Map<String, Object> map) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(an);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field handlerField = handler.getClass().getDeclaredField("memberValues");
        // 因为这个字段是 private final 修饰，所以要打开权限
        handlerField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) handlerField.get(handler);
        Set<String> keys = map.keySet();
        for (String key : keys) {
            // 修改对应属性的值
            memberValues.put(key, map.get(key));
        }

    }


    private static Object getAnnotationAttributeValue(Annotation an, String attributeKey) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(an);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field handlerField = handler.getClass().getDeclaredField("memberValues");
        // 因为这个字段是 private final 修饰，所以要打开权限
        handlerField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) handlerField.get(handler);
        return memberValues.get(attributeKey);
    }

    private static Map getAnnotationAttributeMap(Annotation an) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(an);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field handlerField = handler.getClass().getDeclaredField("memberValues");
        // 因为这个字段是 private final 修饰，所以要打开权限
        handlerField.setAccessible(true);
        return (Map) handlerField.get(handler);
    }

    private static boolean isEquals(Object o1, Object o2) {
        boolean result = false;
        if (o1.getClass().isArray()) {
            Object[] arr1 = (Object[]) o1;
            Object[] arr2 = (Object[]) o2;
            result = Arrays.equals(arr1, arr2);
        } else {
            result = o1.equals(o2);
        }
        return result;
    }


    private static final class AnnotationDefinition {
        private Annotation thisAnnotation;
        private Class<? extends Annotation> annotationType;
        private List<AnnotationDefinition> parentList = new ArrayList<>();
        private Map<String, AttributeMeta> attributeMap = new HashMap<>();
        private Map<String, AliasRegister> aliasRegisterMap = new HashMap<>();
    }

    private static final class AttributeMeta {
        private String attributeName;
        private Object attributeValue;
        private boolean IS_DEFAULT_VALUE = true;
        private String aliasForAttribute;
        private String aliasForValue;
        private Class<? extends Annotation> annotation;
    }

    private static final class AliasRegister {
        private boolean IS_REGIST_ITSELE = true;
        private String attributeName;
        private Object attributeValue;
    }
}

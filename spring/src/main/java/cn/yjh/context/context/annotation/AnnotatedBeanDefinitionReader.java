package cn.yjh.context.context.annotation;


import cn.yjh.annotation.Component;
import cn.yjh.annotation.ComponentScan;
import cn.yjh.annotation.Lazy;
import cn.yjh.annotation.Scope;
import cn.yjh.annotation.support.AnnotationUtils;
import cn.yjh.beans.config.BeanDefinition;
import cn.yjh.beans.config.BeanPostProcessor;
import cn.yjh.beans.support.ScopeType;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-08 10:18
 */
public class AnnotatedBeanDefinitionReader {

    private String[] basePackages = null;
    private Class<?>[] componentClasses = null;
    Map<String, BeanDefinition> beanDefinitionMap = null;

    public AnnotatedBeanDefinitionReader() {

    }

    public AnnotatedBeanDefinitionReader(String... basePackages) {
        this.basePackages = basePackages;
    }

    public AnnotatedBeanDefinitionReader(Class<?>... componentClasses) {
        this.componentClasses = componentClasses;
    }

    public void doRegisterBean(Map<String, BeanDefinition> beanDefinitionMap) {
        if (basePackages == null && componentClasses == null) {
            return;
        }
        Set<String> basePackageList = new HashSet<>();
        if (basePackages != null) {
            for (String basePackage : basePackages) {
                basePackageList.add(basePackage);
            }
        }
        if (componentClasses != null) {
            for (Class<?> componentClass : componentClasses) {
                String[] basePackages = readComponentClass(componentClass);
                for (String basePackage : basePackages) {
                    basePackageList.add(basePackage);
                }
            }
        }
        this.beanDefinitionMap = beanDefinitionMap;
        this.doScan(basePackageList);
    }

    private String[] readComponentClass(Class<?> componentClass) {
        String[] basePackages = null;
        if (componentClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan annotation = componentClass.getAnnotation(ComponentScan.class);
            basePackages = annotation.basePackages();
        }
        return basePackages;
    }

    private void doScan(Set<String> basePackageList) {
        String basePackage = null;
        String basePath = null;
        Iterator<String> iterator = basePackageList.iterator();
        while (iterator.hasNext()) {
            basePackage = iterator.next();
            basePath = basePackage.replaceAll("\\.", "/");
            URL resource = this.getClass().getClassLoader().getResource(basePath);
            File file = new File(resource.getPath());
            loopLoad(file, basePackage);
        }
    }

    private void loopLoad(File file, String basePackage) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                loopLoad(file1, basePackage);
            }
        } else {
            try {
                URL classPath = this.getClass().getClassLoader().getResource("");
                String pathName = file.getAbsolutePath();
                String className = pathName.substring(classPath.getPath().length() - 1, pathName.lastIndexOf("."));
                className = className.replaceAll("\\\\", ".");
                Class<?> clazz = Class.forName(className);
                BeanDefinition beanDefinition = buildBeanDefinition(clazz);
                beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private BeanDefinition buildBeanDefinition(Class<?> clazz) throws Exception {
        String beanName = toLowerCaseFirst(clazz.getSimpleName());
        String beanClassName = clazz.getName();
        ScopeType scope = ScopeType.SINGLETON;
        boolean lazyInit = true;
        if (AnnotationUtils.isAnnotationPresentOnClass(clazz, Component.class)) {
            Component anotation = AnnotationUtils.findAnotationOnClass(clazz, Component.class);
            String value = anotation.value();
            if (value != null && !"".equals(value))
                beanName = value;
            if(clazz.isAssignableFrom(BeanPostProcessor.class)){
                registerBeanPostProcessor(clazz);
            }
        }
        if (clazz.isAnnotationPresent(Scope.class)) {
            scope = clazz.getAnnotation(Scope.class).value();
        }
        if (clazz.isAnnotationPresent(Lazy.class)) {
            lazyInit = clazz.getAnnotation(Lazy.class).value();
        }
        return new BeanDefinition(scope, beanClassName, lazyInit, beanName);
    }

    private void registerBeanPostProcessor(Class<?> clazz) {
    }

    private String toLowerCaseFirst(String beanName) {
        char[] chars = beanName.toCharArray();
        if( 65 <= chars[0] && chars[0]<= 90){
            chars[0] +=  32;
        }
        return String.valueOf(chars);
    }
}

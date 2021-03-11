package cn.yjh.context;

import cn.yjh.annotation.Autowired;
import cn.yjh.annotation.Component;
import cn.yjh.annotation.support.AnnotationUtils;
import cn.yjh.beans.BeanWrapper;
import cn.yjh.beans.config.BeanDefinition;

import cn.yjh.beans.support.DefaultListableBeanFactory;
import cn.yjh.beans.support.ScopeType;
import cn.yjh.context.context.annotation.AnnotatedBeanDefinitionReader;
import cn.yjh.context.support.AbstractApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 16:44
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext{

    // BeanDefinition 存储容器
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    // 单例池
    private final Map<String, Object> singletonObject = new ConcurrentHashMap<>(256);
    // IOC 容器
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(256);

    // 注解 BeanDefintionReader
    private final AnnotatedBeanDefinitionReader reader;



    public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
        this.reader = new AnnotatedBeanDefinitionReader();
        this.refresh();
    }

    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        this.reader = new AnnotatedBeanDefinitionReader(componentClasses);
        this.refresh();
    }

    public AnnotationConfigApplicationContext(String... basePackages) {
        this.reader = new AnnotatedBeanDefinitionReader(basePackages);
        this.refresh();
    }

    /*@Override
    public void refresh() {
        // 1. 加载配置文件，并封装成 BeanDefinition
        this.reader.doRegisterBean(beanDefinitionMap);
        // 2. 将非延迟加载的 bean 加载到IOC容器中
        Set<Map.Entry<String, BeanDefinition>> beanDefinitionEntries = beanDefinitionMap.entrySet();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionEntries){
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }


    }*/

    @Override
    public <T> T getBean(String beanName) {
        // 1. 初始化
        BeanWrapper beanWrapper = instantiateBean(beanName, beanDefinitionMap.get(beanName));
        // 2. 依赖注入
        populateBean(beanName,beanDefinitionMap.get(beanName),beanWrapper);
        return null;
    }

    private BeanWrapper instantiateBean(String beanName, BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        Object instance = null;
        ScopeType scope = beanDefinition.getScope();
        if(scope == ScopeType.SINGLETON){
            if(this.singletonObject.containsKey(beanClassName)){
                instance = this.singletonObject.get(beanClassName);
            }else{
                try{
                    Class<?> clazz = Class.forName(beanClassName);
                    instance = clazz.getDeclaredConstructor().newInstance();
                }catch (Exception e){
                    e.printStackTrace();
                }
                this.singletonObject.put(beanClassName,instance);
                this.singletonObject.put(beanName,instance);
            }
        }else{

        }
        BeanWrapper beanWrapper = new BeanWrapper(instance);

        return beanWrapper;
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper beanWrapper) {
        Object instance = beanWrapper.getWrappedInstance();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = Class.forName(beanClassName);
            if(!AnnotationUtils.isAnnotationPresentOnClass(clazz , Component.class)){
                return;
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                if(!field.isAnnotationPresent(Autowired.class)){

                }else{
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredName = autowired.value();
                    if("".equals(autowiredName)){
                        autowiredName = field.getType().getName();
                    }
                    //field.set(instance,);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

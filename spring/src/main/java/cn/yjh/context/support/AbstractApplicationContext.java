package cn.yjh.context.support;

import cn.yjh.beans.config.BeanDefinition;
import cn.yjh.beans.factory.BeanFactory;
import cn.yjh.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-02 21:03
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private String [] location;

    private Class configClazz;

    private BeanFactory parentBeanFactory;

    // BeanDefinition 存储容器
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);


    public AbstractApplicationContext(){

    }

    public AbstractApplicationContext( BeanFactory parentBeanFactory) {
        if (this.parentBeanFactory != null && this.parentBeanFactory != parentBeanFactory) {
            throw new IllegalStateException("Already associated with parent BeanFactory: " + this.parentBeanFactory);
        } else if (this == parentBeanFactory) {
            throw new IllegalStateException("Cannot set parent bean factory to self");
        } else {
            this.parentBeanFactory = parentBeanFactory;
        }
    }

    public void refresh(){
        // 1. 加载配置文件，并封装成 BeanDefinition
        /*this.reader.doRegisterBean(beanDefinitionMap);
        // 2. 将非延迟加载的 bean 加载到IOC容器中
        Set<Map.Entry<String, BeanDefinition>> beanDefinitionEntries = beanDefinitionMap.entrySet();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionEntries){
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }*/
    }

}

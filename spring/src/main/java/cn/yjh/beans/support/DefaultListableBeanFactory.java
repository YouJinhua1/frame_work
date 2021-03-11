package cn.yjh.beans.support;

import cn.yjh.beans.factory.BeanFactory;
import cn.yjh.beans.factory.ListableBeanFactory;
import cn.yjh.beans.config.BeanDefinition;
import cn.yjh.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-02 21:13
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext implements ListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap;

    public DefaultListableBeanFactory() {
        this.beanDefinitionMap = new ConcurrentHashMap(256);
    }

    public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
        this.beanDefinitionMap = new ConcurrentHashMap(256);

    }


    @Override
    public <T> T getBean(String beanName) {
        return null;
    }
}

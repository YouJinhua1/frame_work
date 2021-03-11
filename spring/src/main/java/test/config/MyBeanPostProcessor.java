package test.config;

import cn.yjh.beans.config.BeanDefinition;
import cn.yjh.beans.config.BeanPostProcessor;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 16:40
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("Bean 初始化之前");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("Bean 初始化之后");
        return bean;
    }
}

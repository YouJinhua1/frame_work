package cn.yjh.beans.config;

/**
 * @description: 后置处理器
 * @author: You Jinhua
 * @create: 2021-02-07 15:44
 */
public interface BeanPostProcessor {


    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }


    default Object postProcessAfterInitialization(Object bean, String beanName)  {
        return bean;
    }
}

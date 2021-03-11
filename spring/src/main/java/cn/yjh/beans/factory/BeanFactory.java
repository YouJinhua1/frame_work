package cn.yjh.beans.factory;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-02 20:56
 */
public interface BeanFactory {

    <T> T getBean(String beanName);
}

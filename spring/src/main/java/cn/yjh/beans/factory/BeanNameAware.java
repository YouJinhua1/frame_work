package cn.yjh.beans.factory;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 15:40
 */
public interface BeanNameAware extends Aware{
    void setBeanName(String beanName);
}

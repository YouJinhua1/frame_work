package cn.yjh.beans.config;

import cn.yjh.beans.support.ScopeType;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-02 21:17
 */
public class BeanDefinition {

    private ScopeType scope;
    private String beanClassName;
    private boolean lazyInit;
    private String factoryBeanName;

    public BeanDefinition(ScopeType scope, String beanClassName, boolean lazyInit, String factoryBeanName) {
        this.scope = scope;
        this.beanClassName = beanClassName;
        this.lazyInit = lazyInit;
        this.factoryBeanName = factoryBeanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }
}

package cn.yjh.beans.factory;

/**
 * @description: 初始化回调
 * @author: You Jinhua
 * @create: 2021-02-07 15:35
 */
public interface InitializingBean {

    // 属性依赖注入之后，执行
    void afterPropertiesSet();
}

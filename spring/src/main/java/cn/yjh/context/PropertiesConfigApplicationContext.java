package cn.yjh.context;

import cn.yjh.context.support.AbstractApplicationContext;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-02 21:37
 */
public class PropertiesConfigApplicationContext extends AbstractApplicationContext {


    public PropertiesConfigApplicationContext(String...locations){
        locations = locations;
        this.refresh();
    }



    @Override
    public <T> T getBean(String beanName) {
        return null;
    }

    @Override
    public void refresh() {
        // 1. 定位配置文件

        // 2、加载配置文件，扫描相关的类，封装成 beanDefinition

        // 3、注册，把配置文件注册到（伪IOC）容器中

        // 4、把非延时加载的类，提前初始化
    }
}

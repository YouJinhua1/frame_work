package cn.yjh.context.context;

import cn.yjh.beans.factory.Aware;
import cn.yjh.context.ApplicationContext;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 19:07
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext);
}

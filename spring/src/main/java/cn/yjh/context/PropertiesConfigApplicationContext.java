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
        // 1. ��λ�����ļ�

        // 2�����������ļ���ɨ����ص��࣬��װ�� beanDefinition

        // 3��ע�ᣬ�������ļ�ע�ᵽ��αIOC��������

        // 4���ѷ���ʱ���ص��࣬��ǰ��ʼ��
    }
}

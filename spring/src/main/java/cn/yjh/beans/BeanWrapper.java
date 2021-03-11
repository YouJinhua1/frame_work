package cn.yjh.beans;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-15 20:14
 */
public class BeanWrapper {

    private Object instatce;

    public BeanWrapper(Object instatce) {
        this.instatce = instatce;
    }



    public Object getWrappedInstance(){
        return instatce;
    }

    public Class<?> getWrappedClass(){
        return null;
    }
}

package test.service;

import cn.yjh.annotation.Autowired;
import cn.yjh.annotation.Service;
import cn.yjh.beans.factory.BeanNameAware;
import cn.yjh.beans.factory.InitializingBean;
import test.entity.User;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-03 14:06
 */
//@MyService(value = "ni",local = "ni",test = "234")
@Service("XXX")
//@MyComponetScan(value = "com.yy",basePackages = "com.yy",basePackageClasses = {User.class},basePackageClassesw={User.class})
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private User user;

    private String serviceBeanName;

    private String userName;

    @Override
    public void setBeanName(String beanName) {
        this.serviceBeanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        this.userName = user.getUserName();
    }

    public String getUser(String name){
        System.out.println(name);
        return name;
    }
}

package test.controller;

import cn.yjh.annotation.Autowired;
import cn.yjh.annotation.Component;
import cn.yjh.annotation.Controller;
import cn.yjh.beans.factory.BeanNameAware;
import cn.yjh.beans.factory.InitializingBean;
import test.service.UserService;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 15:48
 */
@Controller
public class UserController  {

    @Autowired
    private UserService userService;


}

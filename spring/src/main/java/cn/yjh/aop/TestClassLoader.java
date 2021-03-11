package cn.yjh.aop;

import test.service.UserService;

import java.net.URLClassLoader;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-06 22:13
 */
public class TestClassLoader {

    public static void main(String[] args) {
        //JARClassLoader.factory.getLoader().
        ClassPathLoader.loadClasspath();
        UserService userService = new UserService();


        System.out.println(userService.getClass().getClassLoader());
    }
}

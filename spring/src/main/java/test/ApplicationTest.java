package test;

import cn.yjh.context.AnnotationConfigApplicationContext;
import cn.yjh.context.PropertiesConfigApplicationContext;
import test.config.Config;
import test.service.UserService;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 15:55
 */
public class ApplicationTest {

    public static void main(String[] args) {
        /*AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        //AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("test");
        UserService userService = applicationContext.getBean("userService");*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("OK");
            }
        }).start();
        new Thread( ()->{
            System.out.println("nihao");
        }).start();
    }
}

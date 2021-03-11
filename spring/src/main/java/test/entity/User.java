package test.entity;

import cn.yjh.annotation.Component;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-07 15:52
 */
@Component
public class User {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

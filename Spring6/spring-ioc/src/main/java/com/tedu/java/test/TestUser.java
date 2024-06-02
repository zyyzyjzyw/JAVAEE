package com.tedu.java.test;

import com.tedu.java.bean.AnnotationApplicationContext;
import com.tedu.java.bean.ApplicationContext;
import com.tedu.java.service.UserService;

/**
 * @ClassName TestUser
 * @Description TODO
 * @Author zyy
 * @Date 2024/6/1 10:15
 * @Version 1.0
 */
public class TestUser {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationApplicationContext("com.tedu.java");
        UserService bean = (UserService) context.getBean(UserService.class);
        System.out.println(bean);
        bean.add();
    }
}

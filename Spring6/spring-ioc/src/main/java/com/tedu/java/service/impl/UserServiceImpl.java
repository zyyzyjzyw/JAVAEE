package com.tedu.java.service.impl;

import com.tedu.java.anno.Bean;
import com.tedu.java.anno.Di;
import com.tedu.java.dao.UserDao;
import com.tedu.java.service.UserService;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zyy
 * @Date 2024/5/27 21:11
 * @Version 1.0
 */
@Bean
public class UserServiceImpl implements UserService {
    @Di
    private UserDao userDao;
    @Override
    public void add() {
        System.out.println("service......");
        userDao.add();
    }
}

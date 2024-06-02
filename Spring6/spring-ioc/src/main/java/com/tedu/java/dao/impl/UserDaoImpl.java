package com.tedu.java.dao.impl;

import com.tedu.java.anno.Bean;
import com.tedu.java.dao.UserDao;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author zyy
 * @Date 2024/5/27 21:12
 * @Version 1.0
 */
@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("dao......");
    }
}

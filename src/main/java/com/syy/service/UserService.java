package com.syy.service;

import com.syy.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Transactional  //事务注解
    public void insertUser(){
        userDao.insert();
        System.out.println("插入完成");
    }
}

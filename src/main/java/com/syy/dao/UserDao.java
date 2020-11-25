package com.syy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.UUID;

@Resource
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        String sql = "insert into `tbl_user` (username,age) values(?,?)";
        String username = UUID.randomUUID().toString().substring(0,5);
        jdbcTemplate.update(sql,username,19);
        int a=9;
        int b=a/0;
    }
}

package com.syy.service;

import com.syy.dao.BookMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookService {

    /**
     *

    @Qualifier("bookMapper3")
    @Autowired(required = false)//默认按类型找，当找到多个，按类名首字母小写找，找不到就报错了，如果有多个实例，要指定那个用@Qualifier指定名字
    private BookMapper bookMapper;
     */

    @Inject
    private BookMapper bookMapper;

    private String lable = "bookMapper";

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public void print(){
        System.out.println(bookMapper);
    }
}

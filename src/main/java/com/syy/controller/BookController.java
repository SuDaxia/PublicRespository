package com.syy.controller;

import com.syy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    //@Qualifier("bookService2")//指明必须装配bean名为那个的，否则就是空了，会报错
    @Autowired(required=false)
    private BookService bookService3;

    public void func(){
        bookService3.print();
    }
}

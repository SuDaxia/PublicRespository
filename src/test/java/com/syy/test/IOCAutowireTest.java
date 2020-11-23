package com.syy.test;

import com.syy.config.MainConfigAutowired;
import com.syy.dao.BookMapper;
import com.syy.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCAutowireTest {
    AnnotationConfigApplicationContext annotationConfigApplicationContext= new AnnotationConfigApplicationContext(MainConfigAutowired.class);

    @Test
    public void testAutowire(){
        printBeans(annotationConfigApplicationContext);
        BookService bs =  (BookService) annotationConfigApplicationContext.getBean(BookService.class);
        //看看BokService中得 @Autowired生效情况与Configuration中的注入的顺序与效果
        bs.print();
        System.out.println(bs);
        System.out.println("------");
        //拿出容器中的BookMapper，看看容器中的是什么
        BookMapper bm =  annotationConfigApplicationContext.getBean(BookMapper.class);
        System.out.println(bm);
    }

    private void printBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext){
        String[]  definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for(String name: definitionNames){
            System.out.println(name);
        }
        System.out.println("------>");
    }
}

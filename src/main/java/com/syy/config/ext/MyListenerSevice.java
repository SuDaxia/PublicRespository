package com.syy.config.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MyListenerSevice {

    @EventListener({ApplicationEvent.class}) //可限定坚挺那些事件
    public void listen(ApplicationEvent event){
        System.out.println(this.getClass()+"... listen :"+event);
    }

}

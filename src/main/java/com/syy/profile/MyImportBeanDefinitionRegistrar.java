package com.syy.profile;

import com.syy.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean has = registry.containsBeanDefinition("com.syy.bean.Red");
        boolean has2 = registry.containsBeanDefinition("com.syy.bean.Yellow");
        if (has && has2){
            //BeanDefinition 去看有什么实现类，用相关实现类搞一个即可
            //注册一个bean，并指定bean名
            registry.registerBeanDefinition("rainBow", new RootBeanDefinition(RainBow.class));
        }

    }
}

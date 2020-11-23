package com.syy.profile;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//
public class MyImportSelect implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //实现ImportSelector接口，可以返回扫不到的全名的类名，注入到容器中
        return new String[]{"com.syy.bean.Book","com.syy.bean.Car"};
    }
}

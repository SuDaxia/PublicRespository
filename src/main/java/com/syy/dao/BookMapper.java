package com.syy.dao;

import org.springframework.stereotype.Repository;

//操作数据库层、资源层的
@Repository
public class BookMapper {

    private String label="1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BookMapper{");
        sb.append("label='").append(label).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void func(){
        System.out.print(this.getClass()+" call func"+this.label);
    }
}

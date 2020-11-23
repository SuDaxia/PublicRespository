package com.syy.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
    //1基本类型值
    //2 可以SpringEL表达式 #{expression}
    //3 可以对属性、方法、注解、传递参数使用该注解
    //4 @Value("${project.name:傻逼}") 方式程序中设置默认值，以防止配置文件properties文件没有设置
    //配置文件覆盖还需要开启其他东西注解
    @Value("${project.name:傻逼}")
    private String name;

    //表达式
    @Value("#{1+17}")
    private Integer age;

    //也可以表达式
    @Value("#{true}")
    private Boolean flag;

    //可以自动转
    @Value("true")
    private Boolean flag2;

    //可以娶不到配置时复制程序中指定默认值
    @Value("${project.flag3:true}")
    private Boolean flag3;

    //set方法上注解可以赋值，get方法上不行
    private Boolean flag4;

    //float可以自动转
    //@Value("192.8f")
    @Value("192.8")
    private Float money;

    //long类型 只能使用SpringEL
    @Value("#{300000.01}")
    private Long daikuan;

    //传参方式注解不行
    private Long parameter;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag2() {
        return flag2;
    }

    public void setFlag2(Boolean flag2) {
        this.flag2 = flag2;
    }

    public Boolean getFlag3() {
        return flag3;
    }

    public void setFlag3(Boolean flag3) {
        this.flag3 = flag3;
    }

    @Value("true")
    public Boolean getFlag4() {
        return flag4;
    }

    //set方法上面有用，但是get方法上没有用
    @Value("false")
    public void setFlag4(Boolean flag4) {
        this.flag4 = flag4;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getDaikuan() {
        return daikuan;
    }

    public void setDaikuan(Long daikuan) {
        this.daikuan = daikuan;
    }

    public Long getParameter() {
        return parameter;
    }

    //传参这里好像没有用
    public void setParameter(@Value("#{44455.08}")Long parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", flag=").append(flag);
        sb.append(", flag2=").append(flag2);
        sb.append(", flag3=").append(flag3);
        sb.append(", flag4=").append(flag4);
        sb.append(", money=").append(money);
        sb.append(", daikuan=").append(daikuan);
        sb.append(", parameter=").append(parameter);
        sb.append('}');
        return sb.toString();
    }
}

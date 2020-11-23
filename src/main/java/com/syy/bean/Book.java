package com.syy.bean;


public class Book {

    public String name;
    public String publishCompany;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishCompany() {
        return publishCompany;
    }

    public void setPublishCompany(String publishCompany) {
        this.publishCompany = publishCompany;
    }

    public Book() {
    }

    public Book(String name, String publishCompany) {
        this.name = name;
        this.publishCompany = publishCompany;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Book{");
        sb.append("name='").append(name).append('\'');
        sb.append(", publishCompany='").append(publishCompany).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

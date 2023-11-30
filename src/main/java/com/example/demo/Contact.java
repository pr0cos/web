package com.example.demo;

public class Contact {

    String name;
    String number;
    String mail;

    public Contact(String name, String number, String mail) {
        this.name = name;
        this.number = number;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String age) {
        this.mail = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

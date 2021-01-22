package com.haulmont.testtask;

public class Client {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String passport;


    public Client(int id, String name, String phoneNumber, String email, String passport) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passport = passport;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassport() {
        return passport;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}

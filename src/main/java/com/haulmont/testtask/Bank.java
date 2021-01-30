package com.haulmont.testtask;

import java.util.ArrayList;

public class Bank {
    private int id;
    private String name;
    private ArrayList<Credit> creditList = new ArrayList();
    private ArrayList<Client> clients = new ArrayList<>();

    public Bank(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public ArrayList<Credit> getCreditList() {
        return creditList;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreditList(ArrayList<Credit> creditList) {
        this.creditList = creditList;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
}

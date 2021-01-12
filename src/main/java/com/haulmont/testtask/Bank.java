package com.haulmont.testtask;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Credit> creditList = new ArrayList();
    private ArrayList<Client> clients = new ArrayList<>();

    public Bank(ArrayList<Credit> creditList, ArrayList<Client> clients) {
        this.creditList = creditList;
        this.clients = clients;
    }
}

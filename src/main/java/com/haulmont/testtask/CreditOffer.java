package com.haulmont.testtask;

public class CreditOffer {
    private int id;
    private Client client;
    private Credit credit;
    private double creditSum;
    private int months;

    public CreditOffer(int id, Client client, Credit credit, double creditSum) {
        this.id = id;
        this.client = client;
        this.credit = credit;
        this.creditSum = creditSum;
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Credit getCredit() {
        return credit;
    }

    public String getCreditSum() {
        return String.valueOf(creditSum);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public void setCreditSum(String creditSum) {
        this.creditSum = Double.parseDouble(creditSum);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonths() {
        return String.valueOf(months);
    }

    public void setMonths(String months) {
        this.months = Integer.parseInt(months);
    }
    // график платежей ????
}

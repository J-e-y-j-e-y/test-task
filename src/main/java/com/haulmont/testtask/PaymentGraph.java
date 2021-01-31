package com.haulmont.testtask;

public class PaymentGraph {
    int month;
    double monthPayment;
    double creditBody;
    double procentRemains;

    public PaymentGraph(int month, double monthPayment, double creditBody, double procentRemains) {
        this.month = month;
        this.monthPayment = monthPayment;
        this.creditBody = creditBody;
        this.procentRemains = procentRemains;
    }


    public String getMonthPayment() {
        return String.valueOf(monthPayment);
    }

    public String getCreditBody() {
        return String.valueOf(creditBody);
    }

    public String getProcentRemains() {
        return String.valueOf(procentRemains);
    }

    public int getMonth() {
        return month;
    }
}

package com.haulmont.testtask;

public class Credit {
    private int id;
    private double limit;
    private double procent;
    private Bank bank;
    private String bankName;

    public Credit(int id, double limit, double procent, Bank bank) {
        this.id = id;
        this.limit = limit;
        this.procent = procent;
        this.bank = bank;

    }

    public String getLimit() {
        return String.valueOf(limit);
    }

    public String getProcent() {
        return String.valueOf(procent);
    }

    public Bank getBank() {
        return bank;
    }

    public String getBankName(){
        if(bank != null){
            return bank.getName();
        }
        else return null;
    }

    public void setBankName(String name){
        if(bank != null){
           // bank.setName(name);
            bankName = name;
        }
    }

    public int getId() {
        return id;
    }

    public void setLimit(String limit) {
        this.limit = Double.parseDouble(limit);
    }

    public void setProcent(String procent) {
        this.procent = Double.parseDouble(procent);
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return id + "." +
                " limit[" + limit + "]" +
                " procent[" + procent + "]" +
                " bankName[" + bankName + "]";
    }

    public void setId(int id) {
        this.id = id;
    }
}

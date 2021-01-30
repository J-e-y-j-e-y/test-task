package com.haulmont.testtask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankController extends AbstractController{
    private static HashMap<Integer, Bank> banks = new HashMap<>();
    private final String banksTableName = "BANKS";


    @Override
    public HashMap<Integer, Bank> getAll() {
        String query = "SELECT * FROM " + banksTableName;
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Bank bank = new Bank(id, name);
                banks.put(id, bank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return banks;
    }

    public ArrayList<String> getBankNames(){
        ArrayList<String> list = new ArrayList<>();
        HashMap<Integer, Bank> bankHashMap = getAll();
        for(Map.Entry<Integer, Bank> entry : bankHashMap.entrySet()){
            list.add(entry.getValue().getName());
        }
        return list;
    }

    @Override
    public Object update(Object entity) {
        Bank updatedBank = (Bank) entity;
        int bank_id = updatedBank.getId();
        // поменять клиента в хэшмэпе????

        Bank bank = banks.get(bank_id);
        bank.setName(updatedBank.getName());

        String query = "UPDATE " + banksTableName;
        query += " SET name = '" + updatedBank.getName() + "'";
        query += " WHERE " + "id = " + bank_id + ";";
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        if(rows != 1)
            return -1;
        return rows;
    }

    @Override
    public Object getEntityById(Object id) {
        int bank_id = (int) id;
        return banks.get(bank_id);
    }

    @Override
    public boolean delete(Object id) {
        Bank bank = (Bank) id;
        // delete client

        int bank_id = bank.getId();
        banks.remove(bank_id);
        int rows = 0;

        String query = "DELETE FROM " + "ClientsBank";
        query += " WHERE " + "bankId = " + bank_id + ";";
        PreparedStatement ps = getPrepareStatement(query);
        try {
            rows += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        HashMap<Integer,Credit> credit_list = CreditController.getCredits();
        //System.out.println(credit_list.size());
        for(Map.Entry<Integer, Credit> entry : credit_list.entrySet()){
            Credit credit = entry.getValue();
            //System.out.println(credit);
            if(!credit.getBank().getName().equals(bank.getName()))
                continue;
            query = "DELETE FROM " + "CREDITOFFERS";
            query += " WHERE " + "creditId = " + credit.getId() + ";";
            ps = getPrepareStatement(query);
            try {
                rows += ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closePrepareStatement(ps);
            }
        }

        query = "DELETE FROM " + "CREDITS";
        query += " WHERE " + "bankId = " + bank_id + ";";
        ps = getPrepareStatement(query);
        try {
            rows += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        query = "DELETE FROM " + banksTableName;
        query += " WHERE " + "id = " + bank_id + ";";
        ps = getPrepareStatement(query);
        try {
            rows += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return rows == 2 + credit_list.size();
    }


    @Override
    public boolean create(Object entity) {
        Bank bank = (Bank) entity;
        int bank_id = bank.getId();

        String query = "INSERT INTO " + banksTableName;
        query += " VALUES( '" + bank.getId() + "',";
        query += " '" + bank.getName() + "');";
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return rows == 1;
    }

    public static HashMap<Integer, Bank> getBanks() {
        return banks;
    }
}

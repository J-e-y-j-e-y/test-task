package com.haulmont.testtask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreditController extends AbstractController{
    private static HashMap<Integer, Credit> credits = new HashMap<>();
    private String CreditTable = "CREDITS";

    public static HashMap<Integer, Credit> getCredits() {
        return credits;
    }

    @Override
    public HashMap<Integer, Credit> getAll() {
        credits.clear();
        String query = "SELECT " + CreditTable + ".*, BANKS.*";
        query += " FROM BANKS, " + CreditTable;
        query += " WHERE BANKS.id = " + CreditTable + ".bankId;";
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                double limit = rs.getDouble(2);
                double procent = rs.getDouble(3);
                int bankId = rs.getInt(4);
                String bankName = rs.getString(6);
                HashMap<Integer, Bank> banks = BankController.getBanks();
                Bank bank = banks.get(bankId);
                bank.setName(bankName);
                //Bank bank = new Bank(bankId, bankName);

                Credit credit = new Credit(id, limit, procent, bank);
                System.out.println(credit);
                credits.put(id, credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return credits;
    }


    @Override
    public Object update(Object entity) {
        Credit updatedCredit = (Credit) entity;
        int credit_id = updatedCredit.getId();

        Credit credit = credits.get(credit_id);
        credit.setLimit(updatedCredit.getLimit());
        credit.setProcent(updatedCredit.getProcent());
        credit.setBank(updatedCredit.getBank());

        String query = "UPDATE " + CreditTable;
        query += " SET limit = '" + updatedCredit.getLimit() + "',";
        query += " procent = '" + updatedCredit.getProcent() + "',";
        query += " bankId = '" + updatedCredit.getBank().getId() + "',";
        query += " WHERE " + "id = " + credit_id + ";";
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
        int creditId = (Integer) id;
        return credits.get(creditId);
    }

    @Override
    public boolean delete(Object id) {
        Credit credit = (Credit)id;
        int credit_id = credit.getId();

        // delete client
        credits.remove(credit_id);
        int rows = 0;
        String query = "DELETE FROM " + "CREDITOFFERS";
        query += " WHERE " + "creditId = " + credit_id + ";";
        PreparedStatement ps = getPrepareStatement(query);
        try {
            rows += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        query = "DELETE FROM " + CreditTable;
        query += " WHERE " + "id = " + credit_id + ";";
        ps = getPrepareStatement(query);
        try {
            rows += ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return rows == 2;
    }

    @Override
    public boolean create(Object entity) {
        Credit credit = (Credit) entity;
        int credit_id = credit.getId();
        // поменять клиента в хэшмэпе????

        String query = "INSERT INTO " + CreditTable;
        query += " VALUES( '" + credit.getId() + "',";
        query += " '" + credit.getLimit() + "',";
        query += " '" + credit.getProcent() + "',";
        query += " '" + credit.getBank().getId() + "');";
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
}

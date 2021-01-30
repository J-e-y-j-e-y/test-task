package com.haulmont.testtask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CreditOfferController extends AbstractController{
    private final String CreditOfferTableName = "CREDITOFFERS";
    private static HashMap<Integer, CreditOffer> offers = new HashMap<>();

    @Override
    public HashMap getAll() {
        String query = "SELECT * FROM " + CreditOfferTableName + ";";
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int client_id = rs.getInt(2);
                double credit_id = rs.getInt(3);
                double creditSum = rs.getDouble(4);
                HashMap<Integer, Client> clients = ClientController.getClients();
                Client client = clients.get(client_id);
                HashMap<Integer, Credit> credits = CreditController.getCredits();
                Credit credit = credits.get(credit_id);

                //Bank bank = new Bank(bankId, bankName);

                CreditOffer offer = new CreditOffer(id, client, credit, creditSum);
                offers.put(id, offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return offers;
    }

    @Override
    public Object update(Object entity) {
        CreditOffer updatedCredit = (CreditOffer) entity;
        int credit_id = updatedCredit.getId();

        CreditOffer creditOffer = offers.get(credit_id);
        creditOffer.setClient(updatedCredit.getClient());
        creditOffer.setCredit(updatedCredit.getCredit());
        creditOffer.setCreditSum(updatedCredit.getCreditSum());

        String query = "UPDATE " + CreditOfferTableName;
        query += " SET clientId = '" + updatedCredit.getClient().getId() + "',";
        query += " creditId = '" + updatedCredit.getCredit().getId() + "',";
        query += " creditSum = '" + updatedCredit.getCreditSum() + "',";
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
        int offer_id = (int) id;
        return offers.get(offer_id);
    }

    @Override
    public boolean delete(Object id) {
        int offer_id = (int) id;
        CreditOffer of = offers.get(offer_id);
        // delete client
        offers.remove(offer_id);

        String query = "DELETE FROM " + CreditOfferTableName;
        query += " WHERE " + "id = " + offer_id + ";";
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

    @Override
    public boolean create(Object entity) {
        CreditOffer offer = (CreditOffer) entity;
        int offer_id = offer.getId();
        // поменять клиента в хэшмэпе????

        String query = "INSERT INTO " + CreditOfferTableName;
        query += " VALUES( '" + offer.getId() + "',";
        query += " '" + offer.getClient().getId() + "',";
        query += " '" + offer.getCredit().getId() + "',";
        query += " '" + offer.getCreditSum() + "');";
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

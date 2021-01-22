package com.haulmont.testtask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientController extends AbstractController{
    private HashMap<Integer,Client> clients = new HashMap<>();
    private final String  clientsTableName = "CLIENTS";

    @Override
    public HashMap<Integer, Client> getAll() {
        String query = "SELECT * FROM " + clientsTableName;
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phoneNumber = rs.getString(3);
                String email = rs.getString(4);
                String passport = rs.getString(5);

                Client client = new Client(id, name, phoneNumber, email, passport);
                clients.put(id, client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return clients;
    }

    @Override
    public Object update(Object entity) {
        Client updatedClient = (Client) entity;
        int client_id = updatedClient.getId();
        // поменять клиента в хэшмэпе????

        Client client = clients.get(client_id);
        client.setName(updatedClient.getName());
        client.setPhoneNumber(updatedClient.getPhoneNumber());
        client.setEmail(updatedClient.getEmail());
        client.setPassport(updatedClient.getPassport());

        String query = "UPDATE " + clientsTableName;
        query += " SET name = '" + updatedClient.getName() + "',";
        query += " phone = '" + updatedClient.getPhoneNumber() + "',";
        query += " email = '" + updatedClient.getEmail() + "',";
        query += " passport = '" + updatedClient.getPassport() + "' ";
        query += " WHERE " + "id = " + client_id + ";";
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
        int client_id = (int)id;
        return clients.get(client_id);
    }

    @Override
    public boolean delete(Object id) {
        int client_id = (int) id;
        Client client = clients.get(client_id);
        // delete client
        clients.remove(client_id);

        String query = "DELETE FROM " + clientsTableName;
        query += " WHERE " + "client_id = " + client_id + ";";
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
        Client client = (Client) entity;
        int client_id = client.getId();
        // поменять клиента в хэшмэпе????

        String query = "INSERT INTO " + clientsTableName;
        query += " VALUES( '" + client.getId() + "',";
        query += " '" + client.getName() + "',";
        query += " '" + client.getPhoneNumber() + "',";
        query += " '" + client.getEmail() + "',";
        query += " '" + client.getPassport() + "');";
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

    public HashMap<Integer, Client> getClients() {
        return clients;
    }
}

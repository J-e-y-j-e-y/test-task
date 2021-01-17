package com.haulmont.testtask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends AbstractController{
    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    public void getAll() {
        String query = resdToString("sql/getAllClients.sql");
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String phoneNumber = rs.getString(2);
                String email = rs.getString(3);
                String passport = rs.getString(4);

                Client client = new Client(name, phoneNumber, email, passport);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    @Override
    public Object getEntityById(Object id) {
        return null;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        return false;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }
}

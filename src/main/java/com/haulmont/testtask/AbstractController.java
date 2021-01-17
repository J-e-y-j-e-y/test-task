package com.haulmont.testtask;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractController <E, K>{
    private Connection connection = null;

    public AbstractController() {
        try {
            if(connection != null)
                connection = DriverManager.getConnection("jdbc:hsqldb:file:db-data/testdb/", "SA", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract void getAll();
    public abstract E update(E entity);
    public abstract E getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);


    // Получение экземпляра PrepareStatement
    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    // Закрытие PrepareStatement
    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String resdToString(String path){
        File file = new File(path);
        String str = FileUtils.readFileToString(file, "utf-8");
        return "";
    }
}

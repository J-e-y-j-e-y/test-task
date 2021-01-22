package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.HashMap;
import java.util.Map;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private ClientController clientController = new ClientController();

    static{
        try {
            Class.forName("C:\\Users\\user\\Documents\\TestProject\\test-task\\target\\lib\\hsqldb-2.5.1.jar!\\org\\hsqldb\\jdbc\\JDBCDriver.class");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean con = AbstractController.setConnection();
        System.out.println("CONNECTION : " + con);
        AbstractController.createTables();
        AbstractController.insertValues();

    }

    @Override
    protected void init(VaadinRequest request) {


        HashMap<Integer, Client> clients = clientController.getAll();
        //HashMap<Integer, Client> clients = new HashMap<>();
        //clients.put(1, new Client(1, "Jane", "890", "none@mail.ru", "444444"));
        //clients.put(2, new Client(2, "John", "899999", "none@mail.ru", "4434444234"));
        //clients.put(3, new Client(3, "Judit", "844444", "none@mail.ru", "4467674"));
        //clients.put(4, new Client(4, "Jordge", "8354540", "none@mail.ru", "4447676444"));

        VerticalLayout vertLayout = new VerticalLayout();
        HorizontalLayout horizLayout = new HorizontalLayout();
        vertLayout.setSizeFull();
        vertLayout.setMargin(true);


        Grid gridTable = new Grid();
        Grid buttonsGrid = new Grid();

        buttonsGrid.addColumn("Client's name");
        buttonsGrid.addColumn("Client's phone");
        buttonsGrid.addColumn("Client's email");
        buttonsGrid.addColumn("Client's passport");
        for(Map.Entry<Integer, Client> entry : clients.entrySet()){
            Client c = entry.getValue();
            buttonsGrid.addRow(c.getName(), c.getPhoneNumber(), c.getEmail(), c.getPassport());
        }

        horizLayout.setWidthUndefined(); // Default, can be omitted
        horizLayout.setSpacing(false); // Compact layout

        horizLayout.addComponent(buttonsGrid);
        horizLayout.addComponent(gridTable);
        setContent(horizLayout);


        clientController.create(new Client(5, "Jey", "8999999", "jey@yandex.ru", "630567"));
        clientController.update(new Client(1, "Ray", "8999999", "jey@yandex.ru", "630567"));
    }

}
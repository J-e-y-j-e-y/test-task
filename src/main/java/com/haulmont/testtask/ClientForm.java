package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ClientForm extends FormLayout{
    private MainUI ui;
    private ClientController controller;

    private TextField name = new TextField("name");
    private TextField phoneNumber = new TextField("phoneNumber");
    private TextField email = new TextField("email");
    private TextField passport = new TextField("passport");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Client> binder = new Binder<>(Client.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public ClientForm(MainUI ui, ClientController controller){
        this.ui = ui;
        this.controller = controller;

        add.addClickListener(e -> add());
        add.setVisible(false);
        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) add);
        components.addComponent((Component) update);
        components.addComponent((Component) delete);


        this.addComponents(name, phoneNumber, email, passport, components);
        binder.bindInstanceFields(this);
    }
    public void setClient(Client client) {
        binder.setBean(client);
        if (client == null) {
            setVisible(false);
        } else {
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            name.focus();
        }
    }

    public void addButton(){
        setClient(new Client(controller.generateId(), null, null, null, null));
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Client client = binder.getBean();
        int id = controller.generateId();
        client.setId(id);
        controller.create(client);

        ui.updateClients();
        setClient(null);
    }

    public void update(){
        Client client = binder.getBean();
        controller.update(client);

        ui.updateClients();
        setClient(null);
    }
    public void delete(){
        Client client = binder.getBean();
        controller.delete(client);

        ui.updateClients();
        setClient(null);
    }
}

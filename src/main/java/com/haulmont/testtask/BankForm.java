package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import static com.sun.glass.ui.Cursor.setVisible;

public class BankForm extends FormLayout{
    private MainUI ui;
    private BankController controller;

    private TextField name = new TextField("name");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Bank> binder = new Binder<>(Bank.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public BankForm(MainUI ui, BankController controller){
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

        this.addComponents(name, components);
        binder.bindInstanceFields(this);
    }
    public void setBank(Bank bank) {
        binder.setBean(bank);
        if (bank == null) {
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
        setBank(new Bank(controller.generateId(), null));
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Bank bank = binder.getBean();
        int id = controller.generateId();
        bank.setId(id);
        controller.create(bank);

        ui.updateBanks();
        setBank(null);
    }

    public void update(){
        Bank bank = binder.getBean();
        controller.update(bank);

        ui.updateBanks();
        setBank(null);
    }
    public void delete(){
        Bank bank = binder.getBean();
        controller.delete(bank);

        ui.updateBanks();
        setBank(null);
    }

}

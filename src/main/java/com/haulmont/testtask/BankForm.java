package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import static com.sun.glass.ui.Cursor.setVisible;

public class BankForm extends FormLayout{
    private MainUI ui;
    private BankController controller;

    private TextField name = new TextField("name");

    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Bank> binder = new Binder<>(Bank.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public BankForm(MainUI ui, BankController controller){
        this.ui = ui;
        this.controller = controller;

        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
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
            setVisible(true);
            name.focus();
        }
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

package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CreditForm extends FormLayout{
    private MainUI ui;
    private CreditController controller;
    private BankController bankController;

    private TextField limit = new TextField("limit");
    private TextField procent = new TextField("procent");
    private ComboBox bank = new ComboBox("bank");

    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Credit> binder = new Binder<>(Credit.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public CreditForm(MainUI ui, CreditController controller, BankController bankController){
        this.ui = ui;
        this.controller = controller;
        this.bankController = bankController;

        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) update);
        components.addComponent((Component) delete);

        ArrayList<String> bankNames = bankController.getBankNames();
        bank.setItems(bankNames);

        this.addComponents(limit, procent, bank, components);
        binder.bind(limit, Credit::getLimit, Credit::setLimit);
        binder.bind(procent, Credit::getProcent, Credit::setProcent);
        binder.bind(bank, new ValueProvider<Credit, String>() {
            @Override
            public String apply(Credit credit) {
                return credit.getBank().getName();
            }
        }, new Setter<Credit, String>() {
            @Override
            public void accept(Credit credit, String s) {
                credit.getBank().setName(s);
            }
        });
       //binder.bindInstanceFields(this);
    }
    public void setCredit(Credit credit) {
        binder.setBean(credit);

        if (credit == null) {
            setVisible(false);
        } else {
            setVisible(true);
            limit.focus();
        }
    }
    public void update(){
        Credit credit = binder.getBean();
        controller.update(credit);

        ui.updateCredits();
        setCredit(null);
    }
    public void delete(){
        Credit credit = binder.getBean();
        controller.delete(credit);

        ui.updateCredits();
        setCredit(null);
    }
}

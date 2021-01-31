package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreditForm extends FormLayout{
    private MainUI ui;
    private CreditController controller;
    private BankController bankController;

    private TextField limit = new TextField("limit");
    private TextField procent = new TextField("procent");
    private ComboBox<String> bank = new ComboBox<>("bank");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Credit> binder = new Binder<>(Credit.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public CreditForm(MainUI ui, CreditController controller, BankController bankController){
        this.ui = ui;
        this.controller = controller;
        this.bankController = bankController;

        add.addClickListener(e -> add());
        add.setVisible(false);
        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) add);
        components.addComponent((Component) update);
        components.addComponent((Component) delete);

        updateBanks();

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
                HashMap<Integer, Bank> names = bankController.getAll();
                int bankId = -1;
                for(Map.Entry<Integer, Bank> entry : names.entrySet()){
                    if(s.equals(entry.getValue().getName())){
                        bankId = entry.getKey();
                        break;
                    }
                }
                credit.getBank().setId(bankId);
                credit.getBank().setName(s);
            }
        });
       //binder.bindInstanceFields(this);
    }
    public void setCredit(Credit credit) {
        updateBanks();
        binder.setBean(credit);

        if (credit == null) {
            setVisible(false);
        } else {
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            limit.focus();
        }
    }

    public void updateBanks(){
        ArrayList<String> bankNames = bankController.getBankNames();
        bank.setItems(bankNames);
    }

    public void addButton(){
        setCredit(new Credit(controller.generateId(), 0, 0, new Bank(bankController.generateId(), null)));
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Credit credit = binder.getBean();
        int id = controller.generateId();
        credit.setId(id);
        controller.create(credit);

        ui.updateCredits();
        setCredit(null);
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

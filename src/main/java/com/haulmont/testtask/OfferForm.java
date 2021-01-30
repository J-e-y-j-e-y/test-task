package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class OfferForm extends FormLayout {
    private MainUI ui;
    private CreditOfferController controller;
    private ClientController clientController;

   // private TextField name = new TextField("client");
    //private TextField credit = new TextField("credit");
    private TextField creditSum = new TextField("creditSum");

    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    //private Binder<Client> clientBinder = new Binder<>(Client.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public OfferForm(MainUI ui, CreditOfferController controller, ClientController clientController){
        this.ui = ui;
        this.controller = controller;
        this.clientController = clientController;

        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) update);
        components.addComponent((Component) delete);


        this.addComponents(creditSum, components);
        //clientBinder.bind(name, Client::getName, Client::setName);
        binder.bind(creditSum, CreditOffer::getCreditSum, CreditOffer::setCreditSum);
        //binder.bindInstanceFields(this);
    }
    public void setOffer(CreditOffer offer) {
        binder.setBean(offer);

        if (offer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            creditSum.focus();
        }
    }
    public void update(){
        CreditOffer offer = binder.getBean();
        controller.update(offer);

        ui.updateOffers();
        setOffer(null);
    }
    public void delete(){
        CreditOffer offer = binder.getBean();
        controller.delete(offer);

        ui.updateOffers();
        setOffer(null);
    }
}

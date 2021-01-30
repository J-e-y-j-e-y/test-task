package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class OfferForm extends FormLayout {
    private MainUI ui;
    private CreditOfferController controller;
    private ClientController clientController;
    private CreditController creditController;

    private ComboBox<String> client = new ComboBox<>("client");
    private ComboBox<String> credit = new ComboBox<>("credit");
    private TextField creditSum = new TextField("creditSum");

    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    //private Binder<Client> clientBinder = new Binder<>(Client.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public OfferForm(MainUI ui, CreditOfferController controller, ClientController clientController, CreditController creditController){
        this.ui = ui;
        this.controller = controller;
        this.clientController = clientController;
        this.creditController = creditController;

        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) update);
        components.addComponent((Component) delete);

        ArrayList<String> clientsList = new ArrayList<>();
        clientController.getAll().values().stream().forEach((Client client) -> {
                    clientsList.add(client.toString()); });
        client.setItems(clientsList);
        ArrayList<String> creditsList = new ArrayList<>();
        creditController.getAllCreditsWithoutBank().values().stream().forEach((Credit credit) -> {
            creditsList.add(credit.toString()); });
        credit.setItems(creditsList);

        this.addComponents(client, credit, creditSum, components);
        binder.bind(client, new ValueProvider<CreditOffer, String>() {
            @Override
            public String apply(CreditOffer creditOffer) {
                return creditOffer.getClient().toString();
            }
        }, new Setter<CreditOffer, String>() {
            @Override
            public void accept(CreditOffer creditOffer, String s) {

            }
        });
        binder.bind(credit, new ValueProvider<CreditOffer, String>() {
            @Override
            public String apply(CreditOffer creditOffer) {
                return creditOffer.getCredit().toString();
            }
        }, new Setter<CreditOffer, String>() {
            @Override
            public void accept(CreditOffer creditOffer, String s) {

            }
        });
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

package com.haulmont.testtask;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OfferForm extends FormLayout {
    private MainUI ui;
    private CreditOfferController controller;
    private ClientController clientController;
    private CreditController creditController;
    private BankController bankController;


    private ComboBox<String> client = new ComboBox<>("client");
    private ComboBox<String> credit = new ComboBox<>("credit");
    private TextField creditSum = new TextField("creditSum");
    private TextField months = new TextField("months");
    //private TextField monthPayment = new TextField("monthPayment");
    //private TextField creditBody = new TextField("CreditBody");
    //private TextField procentRemains = new TextField("procentRemains");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");
    private Button calculate = new Button("Calculate");

    private Grid<PaymentGraph> graphGrid = new Grid<>(PaymentGraph.class);

    private Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    //private Binder<PaymentGraph> graph = new Binder<>(PaymentGraph.class);

    //private Binder<Client> clientBinder = new Binder<>(Client.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public OfferForm(MainUI ui, CreditOfferController controller, ClientController clientController, CreditController creditController, BankController bankController){
        this.ui = ui;
        this.controller = controller;
        this.clientController = clientController;
        this.creditController = creditController;
        this.bankController = bankController;


        add.addClickListener(e -> add());
        add.setVisible(false);
        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        calculate.addClickListener(e -> calculate());

        graphGrid.setColumns("month", "monthPayment", "creditBody", "procentRemains");
        graphGrid.setWidthFull();
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) add);
        components.addComponent((Component) update);
        components.addComponent((Component) delete);
        components.addComponent((Component) calculate);

        ArrayList<String> clientsList = new ArrayList<>();
        clientController.getAll().values().stream().forEach((Client client) -> {
                    clientsList.add(client.toString()); });
        client.setItems(clientsList);
        ArrayList<String> creditsList = new ArrayList<>();
        creditController.getAll().values().stream().forEach((Credit credit) -> {
            creditsList.add(credit.toString()); });
        credit.setItems(creditsList);

       // VerticalLayout layout = new VerticalLayout();
        //layout.addComponents(monthPayment, creditBody, procentRemains);

        this.addComponents(client, credit, creditSum, months, components, graphGrid);
        binder.bind(client, new ValueProvider<CreditOffer, String>() {
            @Override
            public String apply(CreditOffer creditOffer) {
                return creditOffer.getClient().toString();
            }
        }, new Setter<CreditOffer, String>() {
            @Override
            public void accept(CreditOffer creditOffer, String s) {
                int clientId = Integer.parseInt(s.split(". ")[0]);
                Client client = (Client) clientController.getEntityById(clientId);
                creditOffer.setClient(client);
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
                int creditId = Integer.parseInt(s.split(". ")[0]);
                Credit credit = (Credit) creditController.getEntityById(creditId);
                creditOffer.setCredit(credit);
            }
        });
        binder.bind(creditSum, CreditOffer::getCreditSum, CreditOffer::setCreditSum);
        binder.bind(months, CreditOffer::getMonths, CreditOffer::setMonths);
        //graph.bindInstanceFields(this);
        this.setWidthFull();
    }
    public void setOffer(CreditOffer offer) {
        binder.setBean(offer);

        if (offer == null) {
            setVisible(false);
        } else {
            //monthPayment.setVisible(false);
            //creditBody.setVisible(false);
            //procentRemains.setVisible(false);
            graphGrid.setVisible(false);

            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            creditSum.focus();
        }
    }

     void calculate(){
        //monthPayment.setVisible(true);
        //creditBody.setVisible(true);
        //procentRemains.setVisible(true);
        graphGrid.setVisible(true);

        CreditOffer offer = binder.getBean();
        int monthCount = Integer.parseInt(months.getValue());
        double creditSum = Double.parseDouble(offer.getCreditSum());
        double procent = Double.parseDouble(offer.getCredit().getProcent());

        double interestRate = (procent/12)/100;
        double coef = (interestRate * Math.pow(1 + interestRate, monthCount))/(Math.pow(1 + interestRate, monthCount) - 1);
        double annuity = creditSum * coef;

        double mProcentSum = creditSum * interestRate;
        double mCreditSum = 0;
        double rest = creditSum;
        ArrayList<PaymentGraph> paymentGraphArrayList = new ArrayList<>();
        for(int i = 1; i <= monthCount; i++){
            mCreditSum = annuity - mProcentSum;
            rest -= mCreditSum;
            paymentGraphArrayList.add(new PaymentGraph(i, annuity, mCreditSum, mProcentSum));
            mProcentSum = rest * interestRate;
        }
        graphGrid.setItems(paymentGraphArrayList);
       // monthPayment.setValue(String.valueOf(monthPayment()));
       // creditBody.setValue(String.valueOf(totalCreditSum()));
       // procentRemains.setValue(String.valueOf(totalProcentSum()));
    }

    double totalProcentSum(){
        return 0;
    }

    double totalCreditSum(){
         return 0;
    }

    double monthPayment(){
        return 0;
    }

    public void addButton(){
        Client client = new Client(clientController.generateId(), null, null, null, null);
        Credit credit = new Credit(controller.generateId(), 0, 0, new Bank(bankController.generateId(), null));
        setOffer(new CreditOffer(controller.generateId(), client, credit, 0, 0));
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        CreditOffer offer = binder.getBean();
        int id = controller.generateId();
        offer.setId(id);
        controller.create(offer);

        ui.updateOffers();
        setOffer(null);
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

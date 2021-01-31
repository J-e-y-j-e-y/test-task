package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainUI extends UI {

    private ClientController clientController = new ClientController();
    private BankController bankController = new BankController();
    private CreditController creditController = new CreditController();
    private CreditOfferController offerController = new CreditOfferController(clientController, creditController);


    private Grid<Client> clientsGrid = new Grid<>(Client.class);
    private ClientForm clientForm = new ClientForm(this, clientController);
    private TextField clientfilterText = new TextField();
    HorizontalLayout clientContent = new HorizontalLayout(clientsGrid, clientForm);
    Button addClient = new Button("Add new client");
    HorizontalLayout clientTool = new HorizontalLayout(clientfilterText, addClient);

    private Grid<Bank> banksGrid = new Grid<>(Bank.class);
    private BankForm bankForm = new BankForm(this, bankController);
    private TextField bankfilterText = new TextField();
    HorizontalLayout bankContent = new HorizontalLayout(banksGrid, bankForm);
    Button addBank = new Button("Add new bank");
    HorizontalLayout bankTool = new HorizontalLayout(bankfilterText, addBank);

    private Grid<Credit> creditsGrid = new Grid<>(Credit.class);
    private CreditForm creditForm = new CreditForm(this, creditController, bankController);
    TextField creditfilterText = new TextField();
    HorizontalLayout creditContent = new HorizontalLayout(creditsGrid, creditForm);
    Button addCredit = new Button("Add new credit");
    HorizontalLayout creditTool = new HorizontalLayout(creditfilterText, addCredit);

    private Grid<CreditOffer> offersGrid = new Grid<>(CreditOffer.class);
    private OfferForm offerForm = new OfferForm(this, offerController, clientController, creditController, bankController);
    TextField offerfilterText = new TextField();
    HorizontalLayout offerContent = new HorizontalLayout(offersGrid, offerForm);
    Button addOffer = new Button("Add new credit offer");
    HorizontalLayout offerTool = new HorizontalLayout(offerfilterText, addOffer);

    static{
       /* try {
            Class.forName("C:\\Users\\user\\Documents\\TestProject\\test-task\\target\\lib\\hsqldb-2.5.1.jar!\\org\\hsqldb\\jdbc\\JDBCDriver.class");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        boolean con = AbstractController.setConnection();
        System.out.println("CONNECTION : " + con);
        AbstractController.createTables();
        AbstractController.insertValues();

    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout vertLayout = new VerticalLayout();


        addClient.addClickListener(e -> {
            clientsGrid.asSingleSelect().clear();
            clientForm.addButton();
        });
        addBank.addClickListener(e -> {
            banksGrid.asSingleSelect().clear();
            bankForm.addButton();
        });
        addCredit.addClickListener(e -> {
            creditsGrid.asSingleSelect().clear();
            creditForm.addButton();
        });
        addOffer.addClickListener(e -> {
            offersGrid.asSingleSelect().clear();
            offerForm.addButton();
        });

        clientsGrid.asSingleSelect().addValueChangeListener(event ->
                clientForm.setClient(clientsGrid.asSingleSelect().getValue()));

        banksGrid.asSingleSelect().addValueChangeListener(event ->
                bankForm.setBank(banksGrid.asSingleSelect().getValue()));

        creditsGrid.asSingleSelect().addValueChangeListener(event ->
                creditForm.setCredit(creditsGrid.asSingleSelect().getValue()));

        offersGrid.asSingleSelect().addValueChangeListener(event ->
                offerForm.setOffer(offersGrid.asSingleSelect().getValue()));

        MenuBar bar = new MenuBar();

        Button clientsButton = new Button("Clients");
        clientsButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                clientForm.setClient(null);
                clientsGrid.setColumns("name", "phoneNumber", "email", "passport");
                updateClients();
                clientfilterText.setPlaceholder("Filter by name...");
                clientfilterText.setValueChangeMode(ValueChangeMode.EAGER);

                clientContent.setVisible(true);
                clientTool.setVisible(true);
            }
        });

        Button banksButton = new Button("Banks");
        banksButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                bankForm.setBank(null);
                banksGrid.setColumns("name");
                updateBanks();

                bankfilterText.setPlaceholder("Filter by name...");
                bankfilterText.setValueChangeMode(ValueChangeMode.EAGER);

                bankContent.setVisible(true);
                bankTool.setVisible(true);
            }
        });
        Button creditsButton = new Button("Credits");
        creditsButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                creditForm.setCredit(null);
                creditsGrid.setColumns("limit", "procent", "bankName");
                updateCredits();

                creditfilterText.setPlaceholder("Filter by name...");
                creditfilterText.setValueChangeMode(ValueChangeMode.EAGER);

                creditContent.setVisible(true);
                creditTool.setVisible(true);

            }
        });
        Button creditOffersButton = new Button("CreditOffers");
        creditOffersButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                offerForm.setOffer(null);
                offersGrid.setColumns("client", "credit", "creditSum");
                updateOffers();
                offerfilterText.setPlaceholder("Filter by name...");
                offerfilterText.setValueChangeMode(ValueChangeMode.EAGER);

                offerContent.setVisible(true);
                offerTool.setVisible(true);
            }
        });


        bar.addItem(clientsButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                bankForm.setBank(null);
                bankContent.setVisible(false);
                bankTool.setVisible(false);

                creditForm.setCredit(null);
                creditContent.setVisible(false);
                creditTool.setVisible(false);

                offerForm.setOffer(null);
                offerContent.setVisible(false);
                offerTool.setVisible(false);

                clientsButton.click();
            }
        });
        bar.addItem(banksButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                bankForm.setBank(null);
                bankContent.setVisible(false);
                bankTool.setVisible(false);

                creditForm.setCredit(null);
                creditContent.setVisible(false);
                creditTool.setVisible(false);

                offerForm.setOffer(null);
                offerContent.setVisible(false);
                offerTool.setVisible(false);

                banksButton.click();
            }
        });
        bar.addItem(creditsButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                bankForm.setBank(null);
                bankContent.setVisible(false);
                bankTool.setVisible(false);

                creditForm.setCredit(null);
                creditContent.setVisible(false);
                creditTool.setVisible(false);

                offerForm.setOffer(null);
                offerContent.setVisible(false);
                offerTool.setVisible(false);

                creditsButton.click();
            }
        });
        bar.addItem(creditOffersButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                bankForm.setBank(null);
                bankContent.setVisible(false);
                bankTool.setVisible(false);

                creditForm.setCredit(null);
                creditContent.setVisible(false);
                creditTool.setVisible(false);

                offerForm.setOffer(null);
                offerContent.setVisible(false);
                offerTool.setVisible(false);

                creditOffersButton.click();
            }
        });
        //bar.setSizeFull();

        vertLayout.setWidthUndefined(); // Default, can be omitted
        vertLayout.setSpacing(false); // Compact layout
        vertLayout.addComponent(bar);


        clientContent.setVisible(false);
        clientTool.setVisible(false);
        vertLayout.addComponents(clientTool, clientContent);

        bankContent.setVisible(false);
        bankTool.setVisible(false);
        vertLayout.addComponents(bankTool, bankContent);

        creditContent.setVisible(false);
        creditTool.setVisible(false);
        vertLayout.addComponents(creditTool, creditContent);

        offerContent.setVisible(false);
        offerTool.setVisible(false);
        vertLayout.addComponents(offerTool, offerContent);

        setContent(vertLayout);

    }

    public void updateClients() {
        HashMap<Integer, Client> clients = clientController.getAll();
        clientsGrid.setItems(new ArrayList<>(clients.values()));
    }

    public void updateCredits() {
        HashMap<Integer, Credit> credits = creditController.getAll();
        creditsGrid.setItems(new ArrayList<>(credits.values()));
    }

    public void updateBanks() {
        HashMap<Integer, Bank> banks = bankController.getAll();
        banksGrid.setItems(new ArrayList<>(banks.values()));
    }
    public void updateOffers() {
        HashMap<Integer, CreditOffer> offers = offerController.getAll();
        offersGrid.setItems(new ArrayList<>(offers.values()));
    }
}
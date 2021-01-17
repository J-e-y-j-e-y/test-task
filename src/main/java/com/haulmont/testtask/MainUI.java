package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private ClientController clientController = new ClientController();

    @Override
    protected void init(VaadinRequest request) {
        /*VerticalLayout todosList = new VerticalLayout(); // (1)
        todosList.setSizeFull();
        todosList.setMargin(true);
        TextField taskField = new TextField(); // (2)
        Button addButton = new Button("Add"); // (3)
        todosList.addComponent(new Label("Vaadin Todo"));
        todosList.addComponent(addButton);
        todosList.addComponent(taskField);
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Label checkbox = new Label(taskField.getValue());
                todosList.addComponent(checkbox);
            }
        });
        */
        //addButton.addClickListener(click -> {
            // (4)
            //Checkbox checkbox = new Checkbox(taskField.getValue());
            //todosList.add(checkbox);
        //});




        VerticalLayout vertLayout = new VerticalLayout();
        HorizontalLayout horizLayout = new HorizontalLayout();
        vertLayout.setSizeFull();
        vertLayout.setMargin(true);


        Grid gridTable = new Grid();
        Grid buttonsGrid = new Grid();

        buttonsGrid.addColumn("Options");
        buttonsGrid.addRow("Clients");
        buttonsGrid.addRow("Credits");
        buttonsGrid.addRow("Banks");
        gridTable.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                String rowName = selectionEvent.getSelected().toString();
                System.out.println(rowName);
                if(rowName.equals("Clients")){
                    gridTable.addColumn("Name");
                    gridTable.addColumn("Phone");
                    gridTable.addColumn("Email");
                    gridTable.addColumn("Passport");
                }else if(rowName.equals("Credits")){
                    gridTable.addColumn("Limit");
                    gridTable.addColumn("Procent");
                }else if(rowName.equals("Banks")){

                }
            }
        });

        horizLayout.setWidthUndefined(); // Default, can be omitted
        horizLayout.setSpacing(false); // Compact layout

        horizLayout.addComponent(buttonsGrid);
        horizLayout.addComponent(gridTable);
        setContent(horizLayout);

    }

}
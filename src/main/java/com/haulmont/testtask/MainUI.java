package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout todosList = new VerticalLayout(); // (1)
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
        //addButton.addClickListener(click -> {
            // (4)
            //Checkbox checkbox = new Checkbox(taskField.getValue());
            //todosList.add(checkbox);
        //});



        /*VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        layout.addComponent(new Label("Main UI"));
*/
        setContent(todosList);
    }
}
package com.example.springvaadinflowdemo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "")
@PageTitle("Customers | Vaadin Flow Demo")
public class ListView extends VerticalLayout {

    Grid<Customer> grid = new Grid<>(Customer.class);

    TextField filterText = new TextField();

    private List<Customer> customers = List.of(
            new Customer(1L, "Customer A", "Address A"),
            new Customer(2L, "Customer B", "Address B"),
            new Customer(3L, "Customer C", "Address C"),
            new Customer(4L, "Customer D", "Address D")
    );

    public ListView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
    }

    private void configureGrid() {
        grid.addClassNames("customer-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "address");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setItems(customers);
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("Add customer");

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}

package com.example.springvaadinflowdemo.components;

import com.example.springvaadinflowdemo.customer.model.Customer;
import com.example.springvaadinflowdemo.customer.service.CustomerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "")
@PageTitle("Customers | Vaadin Flow Demo")
public class ListView extends VerticalLayout {

    private final CustomerService customerService;

    private final Grid<Customer> grid = new Grid<>(Customer.class);

    private final TextField filterText = new TextField();

    private CustomerForm form;

    public ListView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateCustomersList();
    }

    private Component getContent() {
        var content = new HorizontalLayout(grid, form);

        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    private void configureGrid() {
        grid.addClassNames("customer-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "email", "address");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureForm() {
        form = new CustomerForm();
        form.setWidth("25em");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCustomersList());

        var addContactButton = new Button("Add customer");

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void updateCustomersList() {
        grid.setItems(customerService.filterCustomers(filterText.getValue()));
    }

}

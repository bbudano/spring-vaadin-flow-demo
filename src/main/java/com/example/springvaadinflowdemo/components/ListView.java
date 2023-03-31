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
        closeEditor();
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

        grid.asSingleSelect().addValueChangeListener(event ->
                editCustomer(event.getValue()));
    }

    private void configureForm() {
        form = new CustomerForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveCustomer);
        form.addDeleteListener(this::deleteCustomer);
        form.addCloseListener(e -> closeEditor());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCustomersList());

        var addCustomerBtn = new Button("Add customer");
        addCustomerBtn.addClickListener(click -> deselectCustomer());

        var toolbar = new HorizontalLayout(filterText, addCustomerBtn);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void deselectCustomer() {
        grid.asSingleSelect().clear();
        editCustomer(new Customer());
    }

    private void editCustomer(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            form.setCustomer(customer);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveCustomer(CustomerForm.SaveEvent event) {
        customerService.saveCustomer(event.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        customerService.deleteCustomer(event.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private void updateCustomersList() {
        grid.setItems(customerService.filterCustomers(filterText.getValue()));
    }

}

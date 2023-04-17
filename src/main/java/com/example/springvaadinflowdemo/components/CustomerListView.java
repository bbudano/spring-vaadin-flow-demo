package com.example.springvaadinflowdemo.components;

import com.example.springvaadinflowdemo.customer.model.Customer;
import com.example.springvaadinflowdemo.customer.service.CustomerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@Route(value = "customers", layout = CustomLayout.class)
@PageTitle("Customers | Vaadin Flow Demo")
public class CustomerListView extends VerticalLayout {

    private final transient CustomerService customerService;

    private final Grid<Customer> grid = new Grid<>(Customer.class);

    private final TextField filter = new TextField();

    private CustomerForm form;

    public CustomerListView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateGridData();
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
        filter.setPlaceholder("Search");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateGridData());

        var addCustomerBtn = new Button("Add customer");
        addCustomerBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addCustomerBtn.addClickListener(click -> addCustomer());

        var toolbar = new HorizontalLayout(filter, addCustomerBtn);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addCustomer() {
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
        updateGridData();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        customerService.deleteCustomer(event.getCustomer());
        updateGridData();
        closeEditor();
    }

    private void updateGridData() {
        grid.setItems(customerService.filterCustomers(filter.getValue()));
    }

}

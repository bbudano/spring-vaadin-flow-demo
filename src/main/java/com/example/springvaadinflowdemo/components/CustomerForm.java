package com.example.springvaadinflowdemo.components;

import com.example.springvaadinflowdemo.customer.model.Customer;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class CustomerForm extends FormLayout {

    Binder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    TextField txtName = new TextField("Name");
    TextField txtAddress = new TextField("Address");
    EmailField txtEmail = new EmailField("Email");

    Button btnSave = new Button("Save");
    Button btnDelete = new Button("Delete");
    Button btnClose = new Button("Close");

    public CustomerForm() {
        addClassName("customer-form");

        binder.bindInstanceFields(this);

        add(txtName, txtAddress, txtEmail, createButtonsLayout());
    }

    public void setCustomer(Customer customer) {
        binder.setBean(customer);
    }

    private HorizontalLayout createButtonsLayout() {
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        btnSave.addClickShortcut(Key.ENTER);
        btnClose.addClickShortcut(Key.ESCAPE);

        btnSave.addClickListener(event -> validateAndSave());
        btnDelete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        btnClose.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> btnSave.setEnabled(binder.isValid()));

        return new HorizontalLayout(btnSave, btnDelete, btnClose);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean())); // <6>
        }
    }

    public abstract static class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private Customer customer;

        protected CustomerFormEvent(CustomerForm source, Customer customer) {
            super(source, false);
            this.customer = customer;
        }

        public Customer getCustomer() {
            return customer;
        }
    }

    public static class SaveEvent extends CustomerFormEvent {
        SaveEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    public static class DeleteEvent extends CustomerFormEvent {
        DeleteEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }

    }

    public static class CloseEvent extends CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}

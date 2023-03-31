package com.example.springvaadinflowdemo.customer.service;

import com.example.springvaadinflowdemo.customer.model.Customer;
import com.example.springvaadinflowdemo.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> filterCustomers(String filterContext) {
        return customerRepository.findByNameContainingIgnoreCase(filterContext);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

}

package com.example.springvaadinflowdemo.customer.repository;

import com.example.springvaadinflowdemo.customer.model.Customer;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepository extends ListCrudRepository<Customer, Integer> {
}

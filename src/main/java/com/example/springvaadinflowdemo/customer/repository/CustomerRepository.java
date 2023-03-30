package com.example.springvaadinflowdemo.customer.repository;

import com.example.springvaadinflowdemo.customer.model.Customer;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CustomerRepository extends ListCrudRepository<Customer, Integer> {

    List<Customer> findByNameContainingIgnoreCase(String filterContext);

}

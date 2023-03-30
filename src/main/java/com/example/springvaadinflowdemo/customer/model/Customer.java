package com.example.springvaadinflowdemo.customer.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Setter(AccessLevel.NONE)
    private Integer id;

    private String name;

    private String email;

    private String address;

}

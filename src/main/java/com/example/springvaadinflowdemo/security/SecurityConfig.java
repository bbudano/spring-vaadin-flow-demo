package com.example.springvaadinflowdemo.security;

import com.example.springvaadinflowdemo.components.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {
        var users = List.of(
                User.withUsername("admin").password("{noop}admin").roles("ADMIN").build(),
                User.withUsername("user").password("{noop}user").roles("USER").build()
        );

        return new InMemoryUserDetailsManager(users);
    }

}

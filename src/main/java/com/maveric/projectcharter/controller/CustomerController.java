package com.maveric.projectcharter.controller;

import com.maveric.projectcharter.config.Constants;
import com.maveric.projectcharter.dto.CustomerDTO;
import com.maveric.projectcharter.exception.ApiRequestException;
import com.maveric.projectcharter.exception.ServiceException;
import com.maveric.projectcharter.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(Constants.CUSTOMER_MAPPING)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerDTO customerDTO)
            throws ApiRequestException, ServiceException {
        String message = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}

package com.maveric.projectcharter.service.impl;

import com.maveric.projectcharter.config.Constants;
import com.maveric.projectcharter.dto.CustomerDTO;
import com.maveric.projectcharter.entity.Customer;
import com.maveric.projectcharter.exception.ServiceException;
import com.maveric.projectcharter.repository.CustomerRepository;
import com.maveric.projectcharter.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String  createCustomer(CustomerDTO customerDTO) {
        try {
            Customer customer = modelMapper.map(customerDTO, Customer.class);
            customerRepository.save(customer);
            return Constants.CUSTOMER_CREATED;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}

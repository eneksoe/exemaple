package com.bta.diplom.service.impl;

import com.bta.diplom.dto.CustomerDto;
import com.bta.diplom.mapper.WebMapper;
import com.bta.diplom.model.Customer;
import com.bta.diplom.repository.CustomerRepository;
import com.bta.diplom.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private WebMapper<CustomerDto, Customer> mapper;

    @Override
    public void create(CustomerDto customer) {
        final Customer entity = mapper.toEntity(customer);
        repository.save(entity);
    }

    @Override
    public CustomerDto update(CustomerDto customer) {
        final String email = customer.getEmail();
        if(email == null || email.isEmpty()){
            throw new RuntimeException("Email must be not Null or Empty!");
        }
        Customer customerFromDb = repository.findByEmail(email);
        if (customerFromDb == null) {
            String message = "Customer with email = " + email + "does not exist!";
            log.warn(message);
            throw new RuntimeException(message);
        }
        customerFromDb.setFirstName(customer.getFirstName());
        customerFromDb.setLastName(customer.getLastName());
        customerFromDb.setTelephone(customerFromDb.getTelephone());
        return mapper.toDto(customerFromDb);
    }

    @Transactional
    @Override
    public void delete(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public List<CustomerDto> getAll() {
        return mapper.toDtos(repository.findAll());
    }
}
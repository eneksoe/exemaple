package com.bta.diplom.service;

import com.bta.diplom.dto.CustomerOrderDto;
import com.bta.diplom.mapper.WebMapper;
import com.bta.diplom.model.CustomerOrder;
import com.bta.diplom.repository.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private WebMapper<CustomerOrderDto, CustomerOrder> mapper;

    @Autowired
    private CustomerOrderRepository repository;

    @Transactional
    @Override
    public void create(CustomerOrderDto customerOrder) {
        customerOrder.setSubmissionDate(ZonedDateTime.now());
        final var orderToCreate = mapper.toEntity(customerOrder);
        repository.save(orderToCreate);
    }

    @Override
    public List<CustomerOrderDto> getAll() {
        return mapper.toDtos(repository.findAll());
    }
}

package com.gre.learn.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gre.learn.dto.Customer;
import com.gre.learn.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Cacheable(value = "CustomerCache", key="#customerId")
	@Override
	public Customer getCustomer(Long customerId) {
		LOG.info("Trying to get customer information for id {} ",customerId);
        return getCustomerData(customerId);
	}
	
	private Customer getCustomerData(final Long id){
        Customer customer = new Customer(id, "Test", "test@gmail.com");
        return  customer;
    }

}

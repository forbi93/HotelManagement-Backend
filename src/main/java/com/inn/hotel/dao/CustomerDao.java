package com.inn.hotel.dao;

import com.inn.hotel.POJO.Customer;
import com.inn.hotel.wrapper.CustomerWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

    List<Customer> getAllCustomer();

    CustomerWrapper getCustomerById(@Param("id") Integer id);
}

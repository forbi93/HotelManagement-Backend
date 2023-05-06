package com.inn.hotel.service;

import com.inn.hotel.POJO.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    ResponseEntity<String> addNewCustomer(Map<String,String> requestMap);

    ResponseEntity<List<Customer>> getAllCustomer(String filterValue);

    ResponseEntity<String> updateCustomer(Map<String,String> requestMap);

    ResponseEntity<String> deleteCustomer(Integer id);
}

package com.inn.hotel.rest;

import com.inn.hotel.POJO.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/customer")
public interface CustomerRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCustomer(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Customer>> getAllCustomer(@RequestParam(required = false)
                                                  String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateCustomer(@RequestBody(required = true)
                                          Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteCustomer(@PathVariable Integer id);


}

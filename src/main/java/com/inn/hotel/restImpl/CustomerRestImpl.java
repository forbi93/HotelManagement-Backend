package com.inn.hotel.restImpl;

import com.inn.hotel.POJO.Category;
import com.inn.hotel.POJO.Customer;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.rest.CustomerRest;
import com.inn.hotel.service.CustomerService;
import com.inn.hotel.utils.HotelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerRestImpl implements CustomerRest {

    @Autowired
    CustomerService customerService;


    @Override
    public ResponseEntity<String> addNewCustomer(Map<String, String> requestMap) {
        try{
            return customerService.addNewCustomer(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomer(String filterValue) {
        try{
            return customerService.getAllCustomer(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCustomer(Map<String, String> requestMap) {
        try{
            return customerService.updateCustomer(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCustomer(Integer id) {
        try{
            return customerService.deleteCustomer(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.inn.hotel.serviceImpl;

import com.google.common.base.Strings;
import com.inn.hotel.JWT.JwtFilter;
import com.inn.hotel.POJO.Customer;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.dao.CustomerDao;
import com.inn.hotel.service.CustomerService;
import com.inn.hotel.utils.HotelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCustomer(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCustomerMap(requestMap, false)) {
                    customerDao.save(getCustomerFromMap(requestMap, false));
                    return HotelUtils.getResponseEntity("Customer Added Successfully", HttpStatus.OK);
                }
            } else {
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Customer getCustomerFromMap(Map<String, String> requestMap, boolean isAdd) {
        Customer customer = new Customer();
        if (isAdd) {
            customer.setId(Integer.parseInt(requestMap.get("id")));
        }
        customer.setName(requestMap.get("name"));
        customer.setLastName(requestMap.get("lastName"));
        customer.setContactNumber(requestMap.get("contactNumber"));
        customer.setDoc(requestMap.get("doc"));
        customer.setDni(requestMap.get("dni"));
        return customer;
    }

    private boolean validateCustomerMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomer(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Inside if");
                return new ResponseEntity<List<Customer>>(customerDao.getAllCustomer(), HttpStatus.OK);
            }
            return new ResponseEntity<>(customerDao.findAll(), HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Customer>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCustomer(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()) {
                if (validateCustomerMap(requestMap,true)){
                    Optional optional = customerDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        customerDao.save(getCustomerFromMap(requestMap,true));
                        return HotelUtils.getResponseEntity("Customer Updated Successfully", HttpStatus.OK);
                    }else {
                        return HotelUtils.getResponseEntity("Customer id does not exist", HttpStatus.OK);
                    }
                }
                return HotelUtils.getResponseEntity(HotelConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else{
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCustomer(Integer id) {
        try{
            if (jwtFilter.isAdmin()) {
                Optional optional = customerDao.findById(id);
                if (!optional.isEmpty()){
                    customerDao.deleteById(id);
                    return HotelUtils.getResponseEntity("Customer Deleted Successfully",HttpStatus.OK);
                }else{
                    return HotelUtils.getResponseEntity("Customer id does not exist.",HttpStatus.OK);
                }
            }else {
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

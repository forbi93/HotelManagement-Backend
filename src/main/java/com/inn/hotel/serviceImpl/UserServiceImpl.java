package com.inn.hotel.serviceImpl;

import com.google.common.base.Strings;
import com.inn.hotel.JWT.CustomersUsersDetailsService;
import com.inn.hotel.JWT.JwtFilter;
import com.inn.hotel.JWT.JwtUtil;

import com.inn.hotel.POJO.User;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.dao.UserDao;
import com.inn.hotel.service.UserService;
import com.inn.hotel.utils.EmailUtils;
import com.inn.hotel.utils.HotelUtils;
import com.inn.hotel.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomersUsersDetailsService customersUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return HotelUtils.getResponseEntity("Successfully Registered.", HttpStatus.OK);
                } else {
                    return HotelUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return HotelUtils.getResponseEntity(HotelConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String,String> requestMap) {
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if (auth.isAuthenticated()){
                if (customersUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customersUsersDetailsService.getUserDetail().getEmail(),
                                    customersUsersDetailsService.getUserDetail().getRole()) + "\"}",
                    HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
//                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return HotelUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                }
                else{
                    HotelUtils.getResponseEntity("User id does not exist", HttpStatus.OK);
                }
            }else {
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
//        allAdmin.remove(jwtFilter.getcurrentUser());
//        if (status!=null && status.equalsIgnoreCase("true")){
//            emailUtils.sendSimpleMessage(jwtFilter.getcurrentUser(), "Account Approved", "USER:- "+user+"\n is aproved by \nADMIN:-"+jwtFilter.getcurrentUser(),allAdmin);
//        } else {
//            emailUtils.sendSimpleMessage(jwtFilter.getcurrentUser(), "Account Disabled", "USER:- "+user+"\n is disabled by \nADMIN:-"+jwtFilter.getcurrentUser(),allAdmin);
//        }
//    }

    @Override
    public ResponseEntity<String> checkToken() {
        return HotelUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User userObj = userDao.findByEmail(jwtFilter.getcurrentUser());
            if (!userObj.equals(null)) {
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return HotelUtils.getResponseEntity("Passowrd Updated Successfully", HttpStatus.OK);
                }
                return HotelUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try{
//            User user = userDao.findByEmail(requestMap.get("email"));
//            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
//                emailUtils.forgotMail(user.getEmail(),"Credentials by Hotel Management System", user.getPassword());
//            return HotelUtils.getResponseEntity("Check your mail for Credentials.",HttpStatus.OK);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}

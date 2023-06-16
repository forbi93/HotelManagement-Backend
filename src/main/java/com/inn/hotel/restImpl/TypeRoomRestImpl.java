package com.inn.hotel.restImpl;

import com.google.common.base.Strings;
import com.inn.hotel.POJO.TypeRoom;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.rest.TypeRoomRest;
import com.inn.hotel.service.TypeRoomService;
import com.inn.hotel.utils.HotelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TypeRoomRestImpl implements TypeRoomRest {

    @Autowired
    TypeRoomService typeRoomService;

    @Override
    public ResponseEntity<String> addNewTypeRoom(Map<String, String> requestMap) {
        try{
            return typeRoomService.addNewTypeRoom(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<TypeRoom>> getAllTypeRoom(String filterValue) {
        try{
            return typeRoomService.getAllTypeRoom(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateTypeRoom(Map<String, String> requestMap) {
        try{
            return typeRoomService.updateTypeRoom(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteTypeRoom(Integer id) {
        try{
            return typeRoomService.deleteTypeRoom(id);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

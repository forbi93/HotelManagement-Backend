package com.inn.hotel.restImpl;

import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.rest.RoomRest;
import com.inn.hotel.service.RoomService;
import com.inn.hotel.utils.HotelUtils;
import com.inn.hotel.wrapper.RoomWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RoomRestImpl implements RoomRest {

    @Autowired
    RoomService roomService;


    @Override
    public ResponseEntity<String> addNewRoom(Map<String, String> requestMap) {
        try{
            return roomService.addNewRoom(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<RoomWrapper>> getAllRoom() {
        try{
            return roomService.getAllRoom();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateRoom(Map<String, String> requestMap) {
        try{
            return roomService.updateRoom(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteRoom(Integer id) {
        try{
            return roomService.deleteRoom(id);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return roomService.updateStatus(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<RoomWrapper>> getByTypeRoom(Integer id) {
        try{
            return roomService.getByTypeRoom(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<RoomWrapper> getRoomById(Integer id) {
        try{
            return roomService.getRoomById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new RoomWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

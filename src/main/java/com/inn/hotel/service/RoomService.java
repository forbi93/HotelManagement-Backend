package com.inn.hotel.service;

import com.inn.hotel.wrapper.RoomWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RoomService {

    ResponseEntity<String> addNewRoom(Map<String,String> requestMap);

    ResponseEntity<List<RoomWrapper>> getAllRoom();

    ResponseEntity<String> updateRoom(Map<String, String> requestMap);

    ResponseEntity<String> deleteRoom(Integer id);

    ResponseEntity<String> updateStatus(Map<String,String> requestMap);

    ResponseEntity<List<RoomWrapper>> getByTypeRoom(Integer id);

    ResponseEntity<RoomWrapper> getRoomById(Integer id);

}

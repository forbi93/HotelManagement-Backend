package com.inn.hotel.service;

import com.inn.hotel.POJO.TypeRoom;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TypeRoomService {

    ResponseEntity<String> addNewTypeRoom(Map<String,String> requestMap);

    ResponseEntity<List<TypeRoom>> getAllTypeRoom(String filterValue);

    ResponseEntity<String> updateTypeRoom(Map<String, String> requestMap);

    ResponseEntity<String> deleteTypeRoom(Integer id);
}

package com.inn.hotel.rest;

import com.inn.hotel.wrapper.RoomWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/room")
public interface RoomRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewRoom(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<RoomWrapper>> getAllRoom();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateRoom(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteRoom(@PathVariable Integer id);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByTypeRoom/{id}")
    ResponseEntity<List<RoomWrapper>> getByTypeRoom(@PathVariable Integer id);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<RoomWrapper> getRoomById(@PathVariable Integer id);
}

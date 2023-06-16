package com.inn.hotel.rest;

import com.inn.hotel.POJO.TypeRoom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/typeroom")
public interface TypeRoomRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewTypeRoom(@RequestBody(required = true)
                                          Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<TypeRoom>> getAllTypeRoom(@RequestParam(required = false)
                                                  String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateTypeRoom(@RequestBody(required = true)
                                          Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteTypeRoom(@PathVariable Integer id);
}

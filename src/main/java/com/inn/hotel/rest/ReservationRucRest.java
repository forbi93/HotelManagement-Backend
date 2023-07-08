package com.inn.hotel.rest;

import com.inn.hotel.POJO.Reservation;
import com.inn.hotel.POJO.ReservationRuc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/reservationRuc")
public interface ReservationRucRest {

    @PostMapping(path = "/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);

    @GetMapping(path = "/getReservations")
    ResponseEntity<List<ReservationRuc>> getReservations();

    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String,Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteReservation(@PathVariable Integer id);

}

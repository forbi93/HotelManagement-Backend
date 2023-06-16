package com.inn.hotel.service;


import com.inn.hotel.POJO.Reservation;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ReservationService {

    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<Reservation>> getReservations();

    ResponseEntity<byte[]> getPdf(Map<String,Object> requestMap);

    ResponseEntity<String> deleteReservation(Integer id);
}

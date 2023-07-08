package com.inn.hotel.service;

import com.inn.hotel.POJO.Reservation;
import com.inn.hotel.POJO.ReservationRuc;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ReservationRucService {

    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<ReservationRuc>> getReservations();

    ResponseEntity<byte[]> getPdf(Map<String,Object> requestMap);

    ResponseEntity<String> deleteReservation(Integer id);
}

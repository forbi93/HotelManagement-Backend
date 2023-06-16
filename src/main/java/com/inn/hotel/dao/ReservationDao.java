package com.inn.hotel.dao;

import com.inn.hotel.POJO.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationDao extends JpaRepository<Reservation, Integer> {

    List<Reservation> getAllReservations();

    List<Reservation> getReservationByUserName(@Param("username") String username);

}

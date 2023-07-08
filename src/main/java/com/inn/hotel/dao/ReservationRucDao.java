package com.inn.hotel.dao;

import com.inn.hotel.POJO.ReservationRuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRucDao extends JpaRepository<ReservationRuc, Integer> {

    List<ReservationRuc> getAllReservationsRuc();

    List<ReservationRuc> getReservationRucByUserName(@Param("username") String username);
}

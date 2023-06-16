package com.inn.hotel.dao;

import com.inn.hotel.POJO.TypeRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRoomDao extends JpaRepository<TypeRoom, Integer> {

    List<TypeRoom> getAllTypeRoom();
}

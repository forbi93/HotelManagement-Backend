package com.inn.hotel.dao;

import com.inn.hotel.POJO.Room;
import com.inn.hotel.wrapper.RoomWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomDao extends JpaRepository<Room, Integer> {

    List<RoomWrapper> getAllRoom();

    @Modifying
    @Transactional
    Integer updateRoomStatus(@Param("status") String status,@Param("id") Integer id);

    List<RoomWrapper> getRoomByTypeRoom(@Param("id") Integer id);

    RoomWrapper getRoomById(@Param("id") Integer id);
}

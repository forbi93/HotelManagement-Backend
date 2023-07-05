package com.inn.hotel.serviceImpl;

import com.inn.hotel.dao.CustomerDao;
import com.inn.hotel.dao.ReservationDao;
import com.inn.hotel.dao.RoomDao;
import com.inn.hotel.dao.TypeRoomDao;
import com.inn.hotel.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    TypeRoomDao typeRoomDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    CustomerDao customerDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String,Object> map = new HashMap<>();
//        map.put("category", categoryDao.count());
//        map.put("product", productDao.count());
//        map.put("bill", billDao.count());
        map.put("typeRoom", typeRoomDao.count());
        map.put("room", roomDao.count());
        map.put("reservation", reservationDao.count());
        map.put("customer", customerDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

package com.inn.hotel.serviceImpl;

import com.inn.hotel.JWT.JwtFilter;
import com.inn.hotel.POJO.Room;
import com.inn.hotel.POJO.TypeRoom;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.dao.RoomDao;
import com.inn.hotel.service.RoomService;
import com.inn.hotel.utils.HotelUtils;
import com.inn.hotel.wrapper.RoomWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomDao roomDao;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> addNewRoom(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateRoomMap(requestMap,false)){
                    roomDao.save(getRoomFromMap(requestMap,false));
                    return HotelUtils.getResponseEntity("Room Added Successfully.", HttpStatus.OK);
                }
                return HotelUtils.getResponseEntity(HotelConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateRoomMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }
            else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Room getRoomFromMap(Map<String, String> requestMap, boolean isAdd) {
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setId(Integer.parseInt(requestMap.get("typeRoomId")));

        Room room = new Room();
        if (isAdd){
            room.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            room.setStatus("true");
        }
        room.setTypeRoom(typeRoom);
        room.setName(requestMap.get("name"));
        room.setDescription(requestMap.get("description"));
        room.setBeds(Integer.parseInt(requestMap.get("beds")));
        room.setObservations(requestMap.get("observations"));
        room.setPrice(Integer.parseInt(requestMap.get("price")));
        return room;
    }

    @Override
    public ResponseEntity<List<RoomWrapper>> getAllRoom() {
        try {
            return new ResponseEntity<>(roomDao.getAllRoom(),HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateRoom(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateRoomMap(requestMap,true)){
                    Optional<Room> optional = roomDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Room room = getRoomFromMap(requestMap,true);
                        room.setStatus(optional.get().getStatus());
                        roomDao.save(room);
                        return HotelUtils.getResponseEntity("Room Updated Successfully", HttpStatus.OK);
                    }else{
                        return HotelUtils.getResponseEntity("Room id does not exist.",HttpStatus.OK);
                    }
                }else{
                    return HotelUtils.getResponseEntity(HotelConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
                }
            }else{
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteRoom(Integer id) {
        try{
            if (jwtFilter.isAdmin()) {
                Optional optional = roomDao.findById(id);
                if (!optional.isEmpty()){
                    roomDao.deleteById(id);
                    return HotelUtils.getResponseEntity("Room Deleted Successfully",HttpStatus.OK);
                }else{
                    return HotelUtils.getResponseEntity("Room id does not exist.",HttpStatus.OK);
                }
            } else {
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                Optional optional = roomDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    roomDao.updateRoomStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return HotelUtils.getResponseEntity("Room Status Updated Successfully", HttpStatus.OK);
                }else{
                    return HotelUtils.getResponseEntity("Room id does not exist",HttpStatus.OK);
                }
            }else{
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<RoomWrapper>> getByTypeRoom(Integer id) {
        try {
            return new ResponseEntity<>(roomDao.getRoomByTypeRoom(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<RoomWrapper> getRoomById(Integer id) {
        try{
            return new ResponseEntity<>(roomDao.getRoomById(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new RoomWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

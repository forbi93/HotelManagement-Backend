package com.inn.hotel.serviceImpl;

import com.google.common.base.Strings;
import com.inn.hotel.JWT.JwtFilter;
import com.inn.hotel.POJO.TypeRoom;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.dao.TypeRoomDao;
import com.inn.hotel.service.TypeRoomService;
import com.inn.hotel.utils.HotelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TypeRoomServiceImpl implements TypeRoomService {

    @Autowired
    TypeRoomDao typeRoomDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewTypeRoom(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if(validateTypeRoomMap(requestMap, false)){
                    typeRoomDao.save(getTypeRoomFromMap(requestMap, false));
                    return HotelUtils.getResponseEntity("Type Room Added Successfully", HttpStatus.OK);
                }
            }else{
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateTypeRoomMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private TypeRoom getTypeRoomFromMap(Map<String, String> requestMap, boolean isAdd) {
        TypeRoom typeRoom = new TypeRoom();
        if (isAdd) {
            typeRoom.setId(Integer.parseInt(requestMap.get("id")));
        }
        typeRoom.setName(requestMap.get("name"));
        return typeRoom;
    }

    @Override
    public ResponseEntity<List<TypeRoom>> getAllTypeRoom(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return new ResponseEntity<List<TypeRoom>>(typeRoomDao.getAllTypeRoom(),HttpStatus.OK);
            }
            return new ResponseEntity<>(typeRoomDao.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<TypeRoom>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateTypeRoom(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateTypeRoomMap(requestMap, true)) {
                    Optional optional = typeRoomDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        typeRoomDao.save(getTypeRoomFromMap(requestMap, true));
                        return HotelUtils.getResponseEntity("Type Room Updated Successfully", HttpStatus.OK);
                    } else {
                        return HotelUtils.getResponseEntity("Type Room id does not exist", HttpStatus.OK);
                    }
                }
                return HotelUtils.getResponseEntity(HotelConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteTypeRoom(Integer id) {
        try{
            if (jwtFilter.isAdmin()) {
                Optional optional = typeRoomDao.findById(id);
                if (!optional.isEmpty()){
                    typeRoomDao.deleteById(id);
                    return HotelUtils.getResponseEntity("Type Room Deleted Successfully",HttpStatus.OK);
                }else{
                    return HotelUtils.getResponseEntity("Type Room id does not exist.",HttpStatus.OK);
                }
            } else {
                return HotelUtils.getResponseEntity(HotelConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

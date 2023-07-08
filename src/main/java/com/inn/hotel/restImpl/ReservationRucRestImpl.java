package com.inn.hotel.restImpl;

import com.inn.hotel.POJO.ReservationRuc;
import com.inn.hotel.constents.HotelConstants;
import com.inn.hotel.rest.ReservationRucRest;
import com.inn.hotel.service.ReservationRucService;
import com.inn.hotel.utils.HotelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReservationRucRestImpl implements ReservationRucRest {

    @Autowired
    ReservationRucService reservationRucService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return reservationRucService.generateReport(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ReservationRuc>> getReservations() {
        try{
            return reservationRucService.getReservations();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try{
            return reservationRucService.getPdf(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteReservation(Integer id) {
        try{
            return reservationRucService.deleteReservation(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HotelUtils.getResponseEntity(HotelConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

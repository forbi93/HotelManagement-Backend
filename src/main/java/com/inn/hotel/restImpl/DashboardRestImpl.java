package com.inn.hotel.restImpl;

import com.inn.hotel.rest.DashboardRest;
import com.inn.hotel.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestImpl implements DashboardRest {

    @Autowired
    DashboardService dashboardService;


    @Override
    public ResponseEntity<Map<String, Object>> getCounnt() {
        return dashboardService.getCount();
    }
}

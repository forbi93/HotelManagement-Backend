package com.inn.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages={
        "com.inn.hotel"})
public class HotelManagementBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementBackendApplication.class, args);
    }

}

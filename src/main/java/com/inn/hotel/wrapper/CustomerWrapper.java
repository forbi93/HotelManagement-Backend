package com.inn.hotel.wrapper;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CustomerWrapper {

    private Integer id;

    private String name;

    private String lastName;

    private String contactNumber;

    private String dni;

    public CustomerWrapper(Integer id,String name,String lastName, String contactNumber, String dni) {
        this.id=id;
        this.name=name;
        this.lastName=lastName;
        this.contactNumber=contactNumber;
        this.dni=dni;
    }
}

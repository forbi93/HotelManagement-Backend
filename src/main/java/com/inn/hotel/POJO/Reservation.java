package com.inn.hotel.POJO;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Reservation.getAllReservations", query = "select r from Reservation r order by r.id desc")

@NamedQuery(name = "Reservation.getReservationByUserName", query = "select r from Reservation r where r.createdBy=:username order by r.id desc")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long servialVersionUID=1L;

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name = "paymentmethod")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "roomdetails",columnDefinition = "json")
    private String roomDetail;

    @Column(name = "date")
    private String date;

    @Column(name = "createdby")
    private String createdBy;
}

package com.inn.hotel.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.io.Serializable;

@NamedQuery(name = "ReservationRuc.getAllReservationsRuc", query = "select r from ReservationRuc r order by r.id desc")

@NamedQuery(name = "ReservationRuc.getReservationRucByUserName", query = "select r from ReservationRuc r where r.createdBy=:username order by r.id desc")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "reservationRuc")
public class ReservationRuc implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "razSocial")
    private String razSocial;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "dateCreated")
    private String dateCreated;

    @Column(name = "address")
    private String address;

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

package com.inn.hotel.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "TypeRoom.getAllTypeRoom", query = "select t from TypeRoom t")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "typeRoom")
public class TypeRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;
}

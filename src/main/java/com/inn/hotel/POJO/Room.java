package com.inn.hotel.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "Room.getAllRoom", query = "select new com.inn.hotel.wrapper.RoomWrapper(r.id,r.name,r.description,r.beds,r.observations,r.price,r.status,r.typeRoom.id,r.typeRoom.name) from Room r")

@NamedQuery(name = "Room.updateRoomStatus", query = "update Room r set r.status=:status where r.id=:id ")

@NamedQuery(name = "Room.getRoomByTypeRoom", query = "select new com.inn.hotel.wrapper.RoomWrapper(r.id,r.name) from Room r where r.typeRoom.id=:id and r.status='true'")

@NamedQuery(name = "Room.getRoomById", query = "select new com.inn.hotel.wrapper.RoomWrapper(r.id,r.name,r.description,r.price) from Room r where r.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "room")
public class Room {

    public static final Long serialVersionUid = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeRoom_fk", nullable = false)
    private TypeRoom typeRoom;

    @Column(name = "description")
    private String description;

    @Column(name = "beds")
    private Integer beds;

    @Column(name = "observations")
    private String observations;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;
}

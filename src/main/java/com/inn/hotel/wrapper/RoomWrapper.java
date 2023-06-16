package com.inn.hotel.wrapper;

import lombok.Data;

@Data
public class RoomWrapper {

    Integer id;

    String name;

    String description;

    Integer beds;

    String observations;

    Integer price;

    String status;

    Integer typeRoomId;

    String typeRoomName;

    public RoomWrapper() {

    }

    public RoomWrapper(Integer id,String name,String description,Integer beds, String observations,Integer price,String status,
                          Integer typeRoomId, String typeRoomName){
        this.id=id;
        this.name=name;
        this.description=description;
        this.beds=beds;
        this.observations=observations;
        this.price=price;
        this.status=status;
        this.typeRoomId = typeRoomId;
        this.typeRoomName = typeRoomName;

    }

    public RoomWrapper(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    public RoomWrapper(Integer id,String name,String description,Integer price){
        this.id=id;
        this.name=name;
        this.description=description;
        this.price=price;
    }

}

package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.model.RoomType;

import javax.persistence.*;

public class RoomDTO {
    private Integer id;
    private Integer roomNumber;
    private RoomType type;

    public RoomDTO(Room room) {
        id = room.getId();
        roomNumber = room.getRoomNumber();
        type = room.getType();
    }

    public RoomDTO(Integer id, Integer roomNumber, RoomType type) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }
}
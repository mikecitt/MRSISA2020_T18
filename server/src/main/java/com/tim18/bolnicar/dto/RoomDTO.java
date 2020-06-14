package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.model.RoomType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

public class RoomDTO {
    private Integer id;
    private Integer roomNumber;
    private RoomType type;
    private Date firstFreeDate;

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

    public Date getFirstFreeDate() {
        return firstFreeDate;
    }

    public void setFirstFreeDate(Date firstFreeDate) {
        this.firstFreeDate = firstFreeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return this.id.equals(roomDTO.id);
    }
}

package com.tim18.bolnicar.service;

import com.tim18.bolnicar.model.Room;

import java.util.List;

public interface RoomService {
    Room save(Room room);
    List<Room> findAll();
    void remove(int id);
    Room findOne(int id);
    Room findByRoomNumber(int roomNumber);
}

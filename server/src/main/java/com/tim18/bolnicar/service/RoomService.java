package com.tim18.bolnicar.service;

import com.tim18.bolnicar.dto.RoomDTO;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.Clinic;
import com.tim18.bolnicar.model.Room;

import java.text.ParseException;
import java.util.List;

public interface RoomService {
    Room save(Room room);
    List<Room> findAll();
    void remove(int id);
    Room findOne(int id);
    Room findByRoomNumber(int roomNumber);
    List<RoomDTO> freeRoomsByDateInterval(Clinic clinic, String dateTime, int duration)
            throws ParseException;
    boolean isRoomAlreadyTaken(Room room, Appointment appointment);
}

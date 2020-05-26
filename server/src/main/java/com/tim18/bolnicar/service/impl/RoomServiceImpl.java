package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.repository.RoomRepository;
import com.tim18.bolnicar.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    public void remove(int id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room findOne(int id) {
        return roomRepository.findById(id).orElseGet(null);
    }

    @Override
    public Room findByRoomNumber(int roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }
}

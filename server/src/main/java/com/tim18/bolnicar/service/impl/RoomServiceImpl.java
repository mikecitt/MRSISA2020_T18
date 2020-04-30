package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.repository.RoomRepository;
import com.tim18.bolnicar.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}

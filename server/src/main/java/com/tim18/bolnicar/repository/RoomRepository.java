package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findByRoomNumber(int roomNumber);
}

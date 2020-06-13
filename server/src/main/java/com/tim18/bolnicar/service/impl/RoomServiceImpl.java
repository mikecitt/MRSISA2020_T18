package com.tim18.bolnicar.service.impl;

import com.tim18.bolnicar.dto.RoomDTO;
import com.tim18.bolnicar.dto.TimeIntervalDTO;
import com.tim18.bolnicar.model.*;
import com.tim18.bolnicar.repository.RoomRepository;
import com.tim18.bolnicar.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        return (List<Room>) roomRepository.findAllByOrderByRoomNumberAsc();
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

    @Override
    public List<RoomDTO> freeRoomsByDateInterval(Clinic clinic, String dateTime, int duration)
            throws ParseException {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        List<RoomDTO> busyRooms = new ArrayList<>();
        if(clinic != null) {
            for (Room room : clinic.getRooms()) {
                if (room.getType() == RoomType.EXAMINATION)
                    roomDTOList.add(new RoomDTO(room));
            }
            for (Appointment appointment : clinic.getAppointments()) {

                if (appointment.getRoom() == null)
                    continue;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date date = sdf.parse(dateTime);
                Date dateEnd = new Date(date.getTime() + TimeUnit.MINUTES.toMillis(duration));
                Date appointmentDate = appointment.getDatetime();
                Date appointmentDateEnd = new Date(appointmentDate.getTime() + TimeUnit.MINUTES.toMillis(appointment.getDuration().longValue()));
                if (date.before(appointmentDateEnd) && appointmentDate.before(dateEnd))
                    busyRooms.add(new RoomDTO(appointment.getRoom()));
            }
            roomDTOList.removeAll(busyRooms);
        }

        return roomDTOList;
    }

    @Override
    public boolean isRoomAlreadyTaken(Room room, Appointment appointment) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(appointment.getDatetime());
        try {
            List<RoomDTO> rooms = freeRoomsByDateInterval(
                    appointment.getClinic(), date,
                    appointment.getDuration());
            return rooms.contains(new RoomDTO(room));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<RoomDTO> findRoomsFreeForDay(Clinic clinic, List<TimeIntervalDTO> intervals) {
        List<RoomDTO> rooms = new ArrayList<RoomDTO>();
        List<Room> allRooms = new ArrayList<Room>(clinic.getRooms());

        for(TimeIntervalDTO interval : intervals) {
            String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(interval.getStart());
            long duration = TimeUnit.MILLISECONDS.toMinutes(
                    interval.getEnd().getTime() - interval.getStart().getTime());
            List<RoomDTO> freeRooms = new ArrayList<RoomDTO>();

            try {
                freeRooms = this.freeRoomsByDateInterval(clinic, date, (int)duration);
                for(RoomDTO room : freeRooms) {
                    room.setFirstFreeDate(interval.getStart());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            freeRooms.removeAll(rooms);
            rooms.addAll(freeRooms);

            if(rooms.size() == allRooms.size())
                break;
        }

        return rooms;
    }
}

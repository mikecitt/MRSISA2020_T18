package com.tim18.bolnicar.component;

import com.tim18.bolnicar.dto.Response;
import com.tim18.bolnicar.dto.RoomDTO;
import com.tim18.bolnicar.model.Appointment;
import com.tim18.bolnicar.model.ClinicAdmin;
import com.tim18.bolnicar.model.Room;
import com.tim18.bolnicar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class CronRequestProccesing {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private DoctorService doctorService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void requestProcess() {
        String emailMessage = "";
        List<Appointment> appointmentList = this.appointmentService.findAll();
        for(Appointment appointment : appointmentList) {
            try {
                List<RoomDTO> rooms = this.roomService.freeRoomsByDateInterval(appointment.getClinic(),
                        sdf.format(appointment.getDatetime()), appointment.getDuration());

                Room room;
                Date newDate = null;
                String moveDate = "";
                if(rooms.size() != 0) {
                    room = this.roomService.findOne(rooms.get(0).getId());
                }
                else {
                    List<RoomDTO> freeRooms = new ArrayList<RoomDTO>();
                    Date current = appointment.getDatetime();
                    while(true) {
                        freeRooms = this.roomService
                                .findRoomsFreeForDay(appointment.getClinic(), this.doctorService
                                        .getFreeDayTime(current, appointment.getDoctor().getId(), appointment.getDuration()));

                        if(freeRooms.size() > 0)
                            break;

                        Calendar c = Calendar.getInstance();
                        c.setTime(current);
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        current = c.getTime();
                    }
                    room = this.roomService.findOne(freeRooms.get(0).getId());
                    newDate = freeRooms.get(0).getFirstFreeDate();
                }

                emailMessage = "Poštovani,\n";
                appointment.setRoom(room);
                this.appointmentService.save(appointment);
                emailMessage += "\nOdobren je zahtev";
                emailMessage += moveDate + " za" + "\n";
                emailMessage += this.appointmentService.appointmentInfo(appointment);
                this.emailService.sendMessages(new String[] {
                                appointment.getPatient().getEmailAddress(),
                                appointment.getDoctor().getEmailAddress()
                        },
                        "[INFO] TERMINI",
                        emailMessage
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

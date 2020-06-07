package com.tim18.bolnicar.dto;

import com.tim18.bolnicar.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Event {
    private String title;
    private String date;
    private String end;

    public static List<Event> convertToEvents(List<Appointment> appointments) {
        List<Event> events = new ArrayList<Event>();

        for(Appointment appointment : appointments) {
            events.add(copyFromAppointment(appointment));
        }

        return events;
    }

    public static Event copyFromAppointment(Appointment appointment) {
        Event event = new Event();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        event.setTitle(appointment.getPatient().getFirstName() +
                " " + appointment.getPatient().getLastName());
        event.setDate(sdf.format(appointment.getDatetime()));
        Calendar c = Calendar.getInstance();
        c.setTime(appointment.getDatetime());
        c.add(Calendar.MINUTE, (int)(appointment.getDuration() * 60));

        event.setEnd(sdf.format(c.getTime()));

        return event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

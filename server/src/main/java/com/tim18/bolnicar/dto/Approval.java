package com.tim18.bolnicar.dto;

import java.util.Date;

public class Approval {
    private int appointmentId;
    private int roomNumber;
    private boolean approved;
    private Date newDate;

    public Approval(int appointmentId, int roomNumber, boolean approved) {
        this.appointmentId = appointmentId;
        this.roomNumber = roomNumber;
        this.approved = approved;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }
}

package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class TimeIntervalDTO {
    private Date start;
    private Date end;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer appointmentId;

    public TimeIntervalDTO(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}

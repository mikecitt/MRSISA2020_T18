package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tim18.bolnicar.model.*;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentRequestDTO {
    private Date start;
    private Date end;
    private Integer appointmentId;
    private Integer doctorId;
    private Integer examinationTypeId;
    private Integer clinicId;

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

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getExaminationTypeId() {
        return examinationTypeId;
    }

    public void setExaminationTypeId(Integer examinationTypeId) {
        this.examinationTypeId = examinationTypeId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }
}

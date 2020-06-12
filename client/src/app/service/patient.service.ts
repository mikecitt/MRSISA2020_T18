import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface MedicalReport {
	id: number;
	description: string;
	diagnoses: string[];
	appointmentId: number;
}

export interface Patient {
	firstName: string;
	lastName: string;
	jmbg: string;
}

// maybe move
export interface ExaminationType {
  id: string;
  name: string;
  price: number;
}

// ...
// end block

export interface Appointment {
  id: number;
  datetime: number;
  duration: number;
  discount: number;
  room: any; // ok, take risk
  type: any;
  medicalReportId: number;
  patientId: number;
  doctor: string;
  clinicName: string;
  patientGrade: number;

}

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class PatientService {


  constructor(private http:HttpClient) { }

  // specify type?
  getMedicalRecord() {
  	//TODO: hardcoded
  	return this.http.get<any>('http://localhost:8080/patient/medicalRecord');
  }

  getPatients() {
    return this.http.get<any>('http://localhost:8080/patient');
  }

  getAppointmentsHistory() {
    return this.http.get<any>('http://localhost:8080/patient/appointments')
  }
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

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

  getMedicalRecord(patientId=undefined) {
  	if (patientId == undefined)
      return this.http.get<any>(`${environment.api_url}/patient/medicalRecord`);
    else
      return this.http.get<any>(`${environment.api_url}/patient/medicalRecord/${patientId}`);
  }

  getPatients() {
    return this.http.get<any>(`${environment.api_url}/patient`);
  }

  getAppointmentsHistory() {
    return this.http.get<any>(`${environment.api_url}/patient/appointments`);
  }
}

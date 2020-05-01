import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface MedicalReport {
	id: number;
	description: string;
	diagnoses: string[];
	appointmentId: number;
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
  	return this.http.get<any>('http://localhost:8080/patient/medicalRecord/3')
  }
}

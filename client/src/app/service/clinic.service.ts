import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

export interface Clinic {
	id: number;
	name: string;
	address: string;
	description: string;
  clinicGrade: number;
}

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class ClinicService {

  constructor(private http:HttpClient) { }

  getClinics() {
  	return this.http.get<Clinic[]>(`${environment.api_url}/clinic`);
  }

	getClinicProfile() {
    return this.http.get<Clinic>(`${environment.api_url}/clinic/profile`);
  }

	updateClinicProfile(payload) {
    return this.http.put(`${environment.api_url}/clinic/profile`, payload, httpOptions);
  }

  addClinic(payload) {
    return this.http.post(`${environment.api_url}/clinic/add`, payload, httpOptions);
  }

  getExaminationClinics(payload) {
    return this.http.get(`${environment.api_url}/clinic/free`, { params: payload, headers: httpOptions.headers });
  }

  gradeClinic(payload) {
    return this.http.post(`${environment.api_url}/clinic/grade`, payload, httpOptions)
  }
  
}

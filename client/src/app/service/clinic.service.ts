import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
  	return this.http.get<Clinic[]>('http://localhost:8080/clinic');
  }

	getClinicProfile() {
    return this.http.get<Clinic>('http://localhost:8080/clinic/profile');
  }

	getClinicIncome(payload) {
		return this.http.get('http://localhost:8080/clinic/income', { params: payload, headers: httpOptions.headers });
	}

	updateClinicProfile(payload) {
    return this.http.put('http://localhost:8080/clinic/profile', payload, httpOptions);
  }

  addClinic(payload) {
    return this.http.post('http://localhost:8080/clinic/add', payload, httpOptions);
  }

  getExaminationClinics(payload) {
    return this.http.get('http://localhost:8080/clinic/free', { params: payload, headers: httpOptions.headers });
  }

  gradeClinic(payload) {
    return this.http.post('http://localhost:8080/clinic/grade', payload, httpOptions)
  }

}

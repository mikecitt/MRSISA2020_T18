import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

import { environment } from './../../environments/environment';

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}


@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  getFreeAppointments(clinicId) {
  	return this.http.get<any>(`${environment.api_url}/appointment/free/${clinicId}`,
  															httpOptions);
  }

	startAppointment() {
		return this.http.get<any>(`${environment.api_url}/appointment/start`,
  															httpOptions);
	}

  bookAppointment(appointmentId) {
  	return this.http.post<any>(`${environment.api_url}/appointment/book/${appointmentId}`,
  															httpOptions)
  }

  requestAppointment(payload) {
    return this.http.post<any>(`${environment.api_url}/appointment/request`, payload, httpOptions);
  }

	addPredefinedAppointment(appointment) {
		return this.http.post<any>(`${environment.api_url}/appointment`, appointment,
																httpOptions)
	}

  // read doctor!
  gradeAppointment(grade) {
    return this.http.post<any>(`${environment.api_url}/appointment/grade`, grade, httpOptions);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}


@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  getFreeAppointments(clinicId) {
  	return this.http.get<any>(`http://localhost:8080/appointment/free/${clinicId}`,
  															httpOptions);
  }

  bookAppointment(appointmentId) {
  	return this.http.post<any>(`http://localhost:8080/appointment/book/${appointmentId}`,
  															httpOptions)
  }

  requestAppointment(payload) {
    return this.http.post<any>('http://localhost:8080/appointment/request', payload);
  }

	addPredefinedAppointment(appointment) {
		return this.http.post<any>(`http://localhost:8080/appointment`, appointment,
																httpOptions)
	}

	getAppointmentRequests() {
	  return this.http.get<any>('http://localhost:8080/appointment/request');
	}
}

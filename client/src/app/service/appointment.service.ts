import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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

	startAppointment(aid) {
		return this.http.get<any>(`${environment.api_url}/appointment/start/${aid}`,
  															httpOptions);
	}

  bookAppointment(appointmentId) {
  	return this.http.post<any>(`${environment.api_url}/appointment/book/${appointmentId}`,
  															httpOptions)
  }

  requestAppointment(payload) {
    return this.http.post<any>(`${environment.api_url}/appointment/request`, payload, httpOptions);
  }

	requestAppointmentByDoctor(payload, patientId) {
    return this.http.post<any>(`${environment.api_url}/appointment/request-doc/${patientId}`, payload, httpOptions);
  }

	addPredefinedAppointment(appointment) {
		return this.http.post<any>(`${environment.api_url}/appointment`, appointment,
																httpOptions)
	}

	getAppointmentRequests() {
	  return this.http.get<any>(`${environment.api_url}/appointment/request`);
	}

	solveRequest(approval) {
	  return this.http.post<any>(`${environment.api_url}/appointment/approve`, approval);
	}

  // read doctor!
  gradeAppointment(grade) {
    return this.http.post<any>(`${environment.api_url}/appointment/grade`, grade, httpOptions);
  }

  canStartAppointment() {
    return this.http.get<any>(`${environment.api_url}/appointment/canStart`, httpOptions);
  }

  getAppointment(aid) {
    return this.http.get<any>(`${environment.api_url}/appointment/${aid}`, httpOptions);
  }

  getDiagnosis() {
    return this.http.get<any>(`${environment.api_url}/appointment/diagnosis`, httpOptions);
  }

  getDrugs() {
    return this.http.get<any>(`${environment.api_url}/appointment/drug`, httpOptions);
  }

  saveAppointment(form) {
    return this.http.post<any>(`${environment.api_url}/appointment/saveAppointment`, form, httpOptions);
  }
}

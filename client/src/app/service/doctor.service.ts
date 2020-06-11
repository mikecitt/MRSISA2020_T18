import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http:HttpClient) { }

  addDoctor(payload) {
  	return this.http.post(`${environment.api_url}/doctor`, payload, httpOptions);
  }

	getAvailableDoctors(datetime, duration, examinationType) {
		return this.http.get(`${environment.api_url}/doctor/available/`+datetime+'/'+duration+'/'+examinationType)
	}

  getDoctors() {
  	return this.http.get(`${environment.api_url}/doctor`);
  }

	removeDoctor(id) {
		return this.http.delete(`${environment.api_url}/doctor/${id}`);
	}
}

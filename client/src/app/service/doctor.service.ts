import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http:HttpClient) { }

  addDoctor(payload) {
  	return this.http.post('http://localhost:8080/doctor', payload, httpOptions);
  }

	getAvailableDoctors(datetime, duration, examinationType) {
		return this.http.get('http://localhost:8080/doctor/available/'+datetime+'/'+duration+'/'+examinationType)
	}

  getDoctors() {
  	return this.http.get('http://localhost:8080/doctor');
  }

	removeDoctor(id) {
		return this.http.delete('http://localhost:8080/doctor/' + id);
	}
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface Clinic {
	id: number;
	name: string;
	address: string;
	description: string;
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

	updateClinicProfile(payload) {
    return this.http.put('http://localhost:8080/clinic/profile', payload);
  }
}

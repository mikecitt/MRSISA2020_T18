import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

export interface RegistrationForm {
	emailAddress: string;
	password: string;
	firstName: string;
	lastName: string;
	address: string;
	city: string;
	country: string;
	contact: string;
	jmbg: string;
}


@Injectable({
  providedIn: 'root'
})
export class AuthService {

	constructor(private http:HttpClient) { }

	register(registration: RegistrationForm) {
		return this.http.post<any>('http://localhost:8080/patient/register', registration)
	}
}

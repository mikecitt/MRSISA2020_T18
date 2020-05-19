import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MedicalService {

  constructor(private http:HttpClient) { }

  getTimeOffs(authority) {
    if(authority == 'ROLE_NURSE') {
      return this.http.get<any>('http://localhost:8080/nurse/timeoff');
    }
    else if(authority == 'ROLE_DOCTOR') {
      return this.http.get<any>('http://localhost:8080/doctor/timeoff');
    }
    else
      return null;
  }
}

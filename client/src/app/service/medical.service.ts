import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MedicalService {

  constructor(private http:HttpClient) { }

  getTimeOffs() {
    return this.http.get<any>('http://localhost:8080/medical/timeoff');
  }

  getEvents() {
    return this.http.get<any>('http://localhost:8080/doctor/events');
  }

  sendVacationRequest(timeOff) {
    return this.http.post<any>('http://localhost:8080/medical/timeoff', timeOff);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MedicalService {

  constructor(private http:HttpClient) { }

  getTimeOffs() {
    return this.http.get<any>(`${environment.api_url}/medical/timeoff`);
  }

  getEvents() {
    return this.http.get<any>(`${environment.api_url}/medical/events`);
  }

  sendVacationRequest(timeOff) {
    return this.http.post<any>(`${environment.api_url}/medical/timeoff`, timeOff);
  }
}

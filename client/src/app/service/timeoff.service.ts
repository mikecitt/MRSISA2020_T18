import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TimeoffService {

  constructor(private http:HttpClient) { }

  getTimeOffs() {
    return this.http.get<any>(`${environment.api_url}/admincl/vacations/`);
  }

  solveTimeOff(accept) {
    return this.http.post<any>(`${environment.api_url}/admincl/vacation/`, accept);
  }

  postAcceptance(acceptance) {
    return this.http.post<any>(`${environment.api_url}/admincl/acceptance`, acceptance);
  }
}

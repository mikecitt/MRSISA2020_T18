import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TimeoffService {

  constructor(private http:HttpClient) { }

  getTimeOffs() {
    return this.http.get<any>('http://localhost:8080/admincl/vacations/');
  }

  solveTimeOff(accept) {
    return this.http.post<any>('http://localhost:8080/admincl/vacation/', accept);
  }

  postAcceptance(acceptance) {
    return this.http.post<any>('http://localhost:8080/admincl/acceptance', acceptance);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CcadminService {

  constructor(private http:HttpClient) { }

  getUnregistered() {
    return this.http.get<any>('http://localhost:8080/patient/unregistered');
  }

  postAcceptance(acceptance) {
    return this.http.post<any>('http://localhost:8080/auth/acceptance', acceptance);
  }
}

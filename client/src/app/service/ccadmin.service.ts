import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CcadminService {

  constructor(private http:HttpClient) { }

  getUnregistered() {
    return this.http.get<any>(`${environment.api_url}/patient/unregistered`);
  }

  postAcceptance(acceptance) {
    return this.http.post<any>(`${environment.api_url}/auth/acceptance`, acceptance);
  }
}

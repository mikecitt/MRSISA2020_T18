import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class NurseService {

  constructor(private http:HttpClient) { }

  addNurse(payload) {
    return this.http.post(`${environment.api_url}/nurse`, payload, httpOptions);
  }

  getNurses() {
    return this.http.get(`${environment.api_url}/nurse`);
  }

  removeNurse(id) {
    return this.http.delete(`${environment.api_url}/nurse/${id}`);
  }
}

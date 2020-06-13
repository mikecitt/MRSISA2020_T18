import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class CladminService {

  constructor(private http: HttpClient) { }

  addClAdmin(data) {
  	return this.http.post<any>(`${environment.api_url}/admincl/add`, 
  															data, 
  															httpOptions);
  }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class CladminService {

  constructor(private http: HttpClient) { }

  addClAdmin(data) {
  	return this.http.post<any>('http://localhost:8080/admincl/add', 
  															data, 
  															httpOptions);
  }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) {
  }

  addAdmin(data) {
  	return this.http.post<any>('http://localhost:8080/admin/add', 
  															data, 
  															httpOptions);
  }
}

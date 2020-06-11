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
export class ExaminationTypeService {

  constructor(private http:HttpClient) { }

  addExaminationType(payload) {
    return this.http.post<any>(`${environment.api_url}/examination-type/add`, payload, httpOptions)
  }

  getExaminationTypes() {
  	return this.http.get<any[]>(`${environment.api_url}/examination-type/`)
  }

	getExaminationType(id) {
  	return this.http.get(`${environment.api_url}/examination-type/${id}`)
  }

	removeExaminationType(id) {
		return this.http.delete(`${environment.api_url}/examination-type/${id}`)
	}

	updateExaminationType(payload) {
    return this.http.put(`${environment.api_url}/examination-type/`, payload);
  }
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class ExaminationTypeService {

  constructor(private http:HttpClient) { }

  addExaminationType(payload) {
    return this.http.post<any>('http://localhost:8080/examination-type/add', payload, httpOptions)
  }

  getExaminationTypes() {
  	return this.http.get<any[]>('http://localhost:8080/examination-type/')
  }

	getExaminationType(id) {
  	return this.http.get('http://localhost:8080/examination-type/' + id)
  }

	removeExaminationType(id) {
		return this.http.delete('http://localhost:8080/examination-type/'+id)
	}

	updateExaminationType(payload) {
    return this.http.put('http://localhost:8080/examination-type/', payload);
  }
}

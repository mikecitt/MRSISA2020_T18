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

  getExaminationTypes() {
  	return this.http.get('http://localhost:8080/examination-type/')
  }

	removeExaminationType(id) {
		return this.http.delete('http://localhost:8080/examination-type/'+id)
	}
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class CodebookService {

  constructor(private http:HttpClient) { }

  addDrug(payload) {
  	return this.http.post<any>('http://localhost:8080/codebook/drug', payload, httpOptions)
  }

  addDiagnosis(payload) {
  	return this.http.post<any>('http://localhost:8080/codebook/diagnosis', payload, httpOptions)
  }
}

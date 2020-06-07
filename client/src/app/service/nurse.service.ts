import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class NurseService {

  constructor(private http:HttpClient) { }

  addNurse(payload) {
    return this.http.post('http://localhost:8080/nurse', payload, httpOptions);
  }

  getNurses() {
    return this.http.get('http://localhost:8080/nurse');
  }

  removeNurse(id) {
    return this.http.delete('http://localhost:8080/nurse/' + id);
  }
}

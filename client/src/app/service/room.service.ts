import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http:HttpClient) { }

  addRoom(payload) {
  	return this.http.post<any>('http://localhost:8080/room/add', payload, httpOptions)
  }

  getRooms() {
  	return this.http.get('http://localhost:8080/room/')
  }

	getExaminationRooms() {
  	return this.http.get('http://localhost:8080/room/examination')
  }

	getRoom(id) {
		return this.http.get('http://localhost:8080/room/'+id)
	}

	removeRoom(id) {
		return this.http.delete('http://localhost:8080/room/'+id)
	}

	updateRoom(payload) {
    return this.http.put('http://localhost:8080/room/', payload);
  }
}

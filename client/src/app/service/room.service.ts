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

  getRooms() {
  	return this.http.get('http://localhost:8080/room/')
  }

	removeRoom(id) {
		return this.http.delete('http://localhost:8080/room/'+id)
	}
}

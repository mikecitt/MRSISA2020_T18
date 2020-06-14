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
export class RoomService {

  constructor(private http:HttpClient) { }

  addRoom(payload) {
  	return this.http.post<any>(`${environment.api_url}/room/add`, payload, httpOptions)
  }

  getRooms() {
  	return this.http.get(`${environment.api_url}/room/`)
  }

	getExaminationRooms() {
  	return this.http.get(`${environment.api_url}/room/examination`)
  }

	getAvailableExaminationRooms(datetime, duration) {
		return this.http.get(`${environment.api_url}/room/availableExamination` + '/' + datetime + '/' + duration)
	}

	getAvailableRooms(datetime, duration) {
		return this.http.get(`${environment.api_url}/room/available` + '/' + datetime + '/' + duration)
	}

	getNewFreeRooms(appointmentId) {
	  return this.http.get(`${environment.api_url}/room/availableRooms/${appointmentId}`);
	}

	getRoom(id) {
		return this.http.get(`${environment.api_url}/room/${id}`)
	}

	removeRoom(id) {
		return this.http.delete(`${environment.api_url}/room/${id}`)
	}

	updateRoom(payload) {
    return this.http.put(`${environment.api_url}/room/`, payload);
  }

	getEvents(id) {
    return this.http.get<any>(`${environment.api_url}/room/events/${id}`);
  }
}

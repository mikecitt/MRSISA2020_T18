import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http:HttpClient) { }

  getLocationDetails(address) {
    return this.http.get<any>('https://nominatim.openstreetmap.org/search?q=' + address + '&format=json');
  }
}

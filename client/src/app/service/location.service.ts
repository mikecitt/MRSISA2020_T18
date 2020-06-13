import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http:HttpClient) { }

  getLocationDetails(address) {
    return this.http.get<any>('https://nominatim.openstreetmap.org/search?q=' + address + '&format=json');
  }
}

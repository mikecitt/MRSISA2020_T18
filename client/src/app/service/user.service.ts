import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, filter, map } from 'rxjs/operators';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { environment } from './../../environments/environment';

const STORAGE_KEY = 'currUser';

export interface Profile {
  emailAddress: string;
  firstName: string;
  lastName: string;
  address: string;
  city: string;
  country: string;
  contact: string;
  jmbg: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser;

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient) {
    this.currentUser = this.storage.get(STORAGE_KEY);
  }

  setupUser(user) {
    this.currentUser = user;
    this.storage.set(STORAGE_KEY, this.currentUser);
  }

  initUser() {
    const promise = this.http.get(`${environment.api_url}/auth/refresh`)
      .pipe(map((res) => {
         if (res['access_token'] !== null) {
           return this.getMyInfo().toPromise()
             .then(user => {
               this.setupUser(user);
             });
         }
       }));
    return promise;
  }

  getMyInfo() {
    return this.http.get(`${environment.api_url}/whoami`)
      .pipe(map(user => {
        this.setupUser(user);
        return user;
      }));
  }

  getProfile() {
    return this.http.get<Profile>(`${environment.api_url}/user/profile`);
  }

  updateProfile(payload) {
    return this.http.put(`${environment.api_url}/user/profile`, payload);
  }

  activateProfile(payload) {
    return this.http.put(`${environment.api_url}/user/activate`, payload);
  }

  getRole() {
    var role = null;
    if(this.currentUser) {
      role = this.currentUser['authorities'][0]['authority'];
    }

    return role;
  }
}

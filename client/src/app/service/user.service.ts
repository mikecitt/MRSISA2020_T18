import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, filter, map } from 'rxjs/operators';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

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
    // privremeno dok se ne uradi logout
    //this.storage.set(STORAGE_KEY, undefined);
    this.currentUser = this.storage.get(STORAGE_KEY);
  }

  setupUser(user) {
    this.currentUser = user;
    this.storage.set(STORAGE_KEY, this.currentUser);
  }

  initUser() {
    const promise = this.http.get('http://localhost:8080/auth/refresh')
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
    return this.http.get('http://localhost:8080/whoami')
      .pipe(map(user => {
        this.setupUser(user);
        return user;
      }));
  }

  getProfile() {
    return this.http.get<Profile>('http://localhost:8080/user/profile');
  }

  updateProfile(payload) {
    return this.http.put('http://localhost:8080/user/profile', payload);
  }
}

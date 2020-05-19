import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders, HttpClient } from '@angular/common/http';
import {map} from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { UserService } from './user.service';

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

export interface RegistrationForm {
	emailAddress: string;
	password: string;
	firstName: string;
	lastName: string;
	address: string;
	city: string;
	country: string;
	contact: string;
	jmbg: string;
}


const COOKIE_NAME = 'token';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

	private access_token = null;

	constructor(
	  private userService:UserService,
		private http:HttpClient,
		private cookieService: CookieService,
		private router: Router,
		) {
		this.access_token = cookieService.get(COOKIE_NAME);
		if(!this.access_token) {
		  userService.setupUser(null);
		}
	}

	register(registration: RegistrationForm) {
		return this.http.post<any>('http://localhost:8080/auth/register', registration)
	}

	login(user) {
		const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    // const body = `username=${user.username}&password=${user.password}`;
    const body = {
      'username' : user.email,
      'password' : user.password
    };

    return this.http.post('http://localhost:8080/auth/login', body)
      .pipe(map((res) => {
        console.log('Login success:' + res['accessToken']);
        this.access_token = res['accessToken'];
        const dateNow = new Date();
        dateNow.setMinutes(dateNow.getMinutes() + (res['expiresIn'] / 60000));
        this.cookieService.set(COOKIE_NAME, this.access_token, dateNow);
        console.log(dateNow);
        this.userService.getMyInfo().subscribe(() => {
          this.router.navigate(['/']);
        });
      }));
	}

	logout() {
	  this.access_token = null;
	  this.cookieService.delete(COOKIE_NAME);
	  this.userService.setupUser(null);
	  this.router.navigate(['/login']);
	}

	tokenIsPresent() {
    return this.access_token != undefined && this.access_token != null;
  }

  getToken() {
    return this.access_token;
  }

  whoAmI() {
  	return this.http.get<any>('http://localhost:8080/whoami')
  }

  getCurrentRoute() {
    return this.router.url;
  }
}

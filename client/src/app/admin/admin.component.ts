import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {

  firstname:string = '';
  lastname:string = '';
  username:string = '';
  email:string = '';
  password:string = '';
  repeat:string = '';
  message:string = null;

  constructor(private http:HttpClient) { }

  ngOnInit() {}

  addAdmin() {
    var formData = {
      "firstname": this.firstname,
      "lastname": this.lastname,
      "username": this.username,
      "email": this.email,
      "password": this.password,
      "repeat": this.repeat
    }

    return this.http.post<any>('http://localhost:8080/admin/add', formData).subscribe(data => {
      if(data['message'] == 'true') {
        this.message = "Dodat admin"
      }
      else {
        this.message = "Admin vec postoji"
      }
    });
  }

}

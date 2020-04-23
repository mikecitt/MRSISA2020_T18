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
  address:string = '';
  city:string = '';
  country:string = '';
  contact:string = '';
  jmbg:string = '';
  email:string = '';
  password:string = '';
  repeat:string = '';
  message:string = null;

  constructor(private http:HttpClient) { }

  ngOnInit() {}

  addAdmin() {
    var formData = {
      "emailAddress": this.email,
      "password": this.password,
      "firstName": this.firstname,
      "lastName": this.lastname,
      "address": this.address,
      "city": this.city,
      "country": this.country,
      "contact": this.contact,
      "jmbg": this.jmbg
    }
    console.log(formData);
    if(this.password == this.repeat)
      return this.http.post<any>('http://localhost:8080/admin/add', formData).subscribe(data => {
            if(data['message'] == 'true') {
              this.message = "Dodat admin"
            }
            else {
              this.message = "Admin vec postoji"
            }
          });
    else {
      this.message = "Lozinke se ne poklapaju"
    }
  }

}

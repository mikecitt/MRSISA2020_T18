import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-admincl',
  templateUrl: './admincl.component.html',
  styleUrls: ['./admincl.component.css']
})

export class AdminClComponent implements OnInit {

  firstname:string = '';
  lastname:string = '';
  email:string = '';
  password:string = '';
  repeat:string = '';
  address:string = '';
  city:string = '';
  country:string = '';
  jmbg:string = '';
  contact:string = '';
  message:string = null;

  constructor(private http:HttpClient) { }

  ngOnInit() { }

  addDoctor() {
    var formData = {
      "firstName"   : this.firstname,
      "lastName"    : this.lastname,
      "emailAddress": this.email,
      "password"    : this.password,
      "address"     : this.address,
      "city"        : this.city,
      "country"     : this.country,
      "jmbg"        : this.jmbg,
      "contact"     : this.contact
    }

    if(this.firstname == "" || this.lastname == "" || this.email == "" ||
      this.password == "" || this.repeat == "" || this.address == "" ||
      this.city == "" || this.country == "" || this.contact == "") {
      this.message = "Sva polja moraju biti popunjena.";
    }
    else if(this.password == this.repeat)
      return this.http.post<any>('http://localhost:8080/doctor/add', formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Lekar uspesno dodat."
        }
        else {
          this.message = "Lekar vec postoji."
        }
      });
    else {
      this.message = "Lozinke se ne poklapaju."
    }
  }

}

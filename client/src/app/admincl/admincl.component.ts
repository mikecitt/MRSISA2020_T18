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
  username:string = '';
  email:string = '';
  password:string = '';
  repeat:string = '';
  message:string = null;

  constructor(private http:HttpClient) { }

  ngOnInit() { }

  addDoctor() {
    var formData = {
      "firstname":this.firstname,
      "lastname": this.lastname,
      "username": this.username,
      "email":    this.email,
      "password": this.password
    }

    if(this.firstname == "" || this.lastname == "" || this.username == ""
    || this.email == "" || this.password == "" || this.repeat == "") {
      this.message = "Sva polja moraju biti popunjena.";
    }
    else if(this.password == this.repeat)
      return this.http.post<any>('http://localhost:8080/admincl/add', formData).subscribe(data => {
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

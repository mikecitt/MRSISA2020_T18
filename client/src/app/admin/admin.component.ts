import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { AdminService } from '../service';

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
  alertType:string = null;

  constructor(private service:AdminService) { }

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
      /*
      return this.http.post<any>('http://localhost:8080/admin/add', formData).subscribe(data => {
            if(data['message'] == 'true') {
              this.message = "Admin uspešno dodat"
              this.alertType = "success"
            }
            else {
              this.message = "Admin nije dodat ili već postojeći"
              this.alertType = "danger"
            }
          });
      */
      this.service.addAdmin(formData).subscribe(data => {
        if(data['message'] == 'true') {
          this.message = "Admin uspešno dodat"
          this.alertType = "success"
        }
        else {
          this.message = "Admin nije dodat ili već postojeći"
          this.alertType = "danger"
        }
      });
      else {
        this.message = "Lozinke se ne poklapaju"
        this.alertType = "warning"
      }
    }

  }

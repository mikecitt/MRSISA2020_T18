import { Component, OnInit } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { Subject } from 'rxjs';
import { DoctorService } from '../service';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css'],
  animations: [
    trigger('smoothCollapse', [
      state('initial', style({
        height:'0',
        overflow:'hidden',
        opacity:'0'
      })),
      state('final', style({
        overflow:'hidden',
        opacity:'1'
      })),
      transition('initial=>final', animate('750ms')),
      transition('final=>initial', animate('750ms'))
    ]),
  ]
})
export class DoctorComponent implements OnInit {

  eventsSubject: Subject<void> = new Subject<void>();

  public isCollapsed = true;

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

  constructor(private service:DoctorService) { }

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
      return this.service.addDoctor(formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Lekar uspesno dodat."
          this.eventsSubject.next();
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

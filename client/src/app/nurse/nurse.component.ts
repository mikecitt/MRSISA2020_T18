import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { Subject } from 'rxjs';
import { NurseService } from '../service';
@Component({
  selector: 'app-nurse',
  templateUrl: './nurse.component.html',
  styleUrls: ['./nurse.component.css']
})
export class NurseComponent implements OnInit {

  eventsSubject: Subject<void> = new Subject<void>();

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

  constructor(private service:NurseService) { }

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
      return this.service.addNurse(formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Medicinska sestra uspešno dodata."
          this.eventsSubject.next();
        }
        else {
          this.message = "Medicinska sestra već postoji."
        }
      });
    else {
      this.message = "Lozinke se ne poklapaju."
    }
  }
}

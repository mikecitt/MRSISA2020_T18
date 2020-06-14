import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";
import { AdminService } from '../service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {
  form = this.formBuilder.group({
    emailAddress: ['', Validators.compose([Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")])],
    password: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
    repeat: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
    firstName: ['', Validators.compose([Validators.required])],
    lastName: ['', Validators.compose([Validators.required])],
    address: ['', Validators.compose([Validators.required])],
    city: ['', Validators.compose([Validators.required])],
    country: ['', Validators.compose([Validators.required])],
    contact: ['', Validators.compose([Validators.required, Validators.pattern("[+]?^[0-9]+"),])],
    jmbg: ['', Validators.compose([Validators.required, Validators.minLength(13), Validators.pattern("^[0-9]+"), Validators.maxLength(13)])],
  });


  message:string = null;
  alertType:string = null;

  errorRePassword = false;

  constructor(private service:AdminService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {}

  addAdmin() {
    this.errorRePassword = false;

    if (this.form.controls['repeat'].value !== this.form.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    let formObj = this.form.getRawValue();
    delete formObj['repeat'];

    this.service.addAdmin(formObj).subscribe(data => {
      if(data['message'] == 'true') {
        this.message = "Admin uspešno dodat."
        this.alertType = "success"
      }
      else {
        this.message = "Korisnik već postoji."
        this.alertType = "danger"
      }
    });
  }
}

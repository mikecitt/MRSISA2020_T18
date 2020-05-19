import { Component, OnInit } from '@angular/core';
import { AuthService, RegistrationForm } from '../service';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
	form: FormGroup;

	success: string;
	fail: string;

  errorRePassword: boolean;

  constructor(private messenger: AuthService, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      emailAddress: ['', Validators.compose([Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
      rePassword: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
      firstName: ['', Validators.compose([Validators.required])],
      lastName: ['', Validators.compose([Validators.required])],
      address: ['', Validators.compose([Validators.required])],
      city: ['', Validators.compose([Validators.required])],
      country: ['', Validators.compose([Validators.required])],
      contact: ['', Validators.compose([Validators.required, Validators.pattern("[+]?^[0-9]+"),])],
      jmbg: ['', Validators.compose([Validators.required, Validators.minLength(13), Validators.pattern("^[0-9]+"), Validators.maxLength(13)])],
    });
  }

  ngOnInit(): void {
  }

  register() {
    if (this.form.controls['rePassword'].value !== this.form.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    this.success = null;
    this.fail = null;

    let formObj = this.form.getRawValue();
    delete formObj['rePassword'];

    this.messenger.register(formObj).subscribe(data => {
      if (data['status'] == 'ok')
        this.success = 'Zahtev uspesno poslat.';
    },
    (err) => { this.fail = 'Poslati podaci nisu u validni.' })
  }

  focusField() {
    this.errorRePassword = false;
  }
}

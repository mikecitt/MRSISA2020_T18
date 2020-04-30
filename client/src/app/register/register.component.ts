import { Component, OnInit } from '@angular/core';
import { AuthService, RegistrationForm } from '../auth.service';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
	form: RegistrationForm = {
		emailAddress: '',
		password: '',
		firstName: '',
		lastName: '',
		address: '',
		city: '',
		country: '',
		contact: '',
		jmbg: ''
	};

	rePassword: string = ''

	success: string;
	fail: string;

  constructor(private messenger: AuthService) { }

  ngOnInit(): void {
  }

  register() {
  	this.success = null;
  	this.fail = null;

  	this.messenger.register(this.form).subscribe(data => {
  		if (data['status'] == 'ok')
  			this.success = 'Zahtev uspesno poslat.';
  	},
  	(err) => { this.fail = 'Poslati podaci nisu u validnom formatu.' })
  }
}

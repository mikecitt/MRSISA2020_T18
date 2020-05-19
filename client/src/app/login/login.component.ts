import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { AuthService } from '../service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
	form: FormGroup;
	errorMessage: string;

  constructor(
    private formBuilder: FormBuilder,
    private service: AuthService
  ) {
  	this.form = this.formBuilder.group({
      email: ['', Validators.compose([Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])]
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  	console.log(this.form.value)

  	this.service.login(this.form.value)
  	.subscribe(data => {

        },
        error => {
          //this.submitted = false;
          this.errorMessage = 'Neispravan email ili lozinka.';
        });
  }

}

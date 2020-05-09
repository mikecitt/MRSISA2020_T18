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

  constructor(private formBuilder: FormBuilder, private service: AuthService) {
  	this.form = this.formBuilder.group({
      email: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  	console.log(this.form.value)

  	this.service.login(this.form.value)
  	.subscribe(data => {
          // you are in
          // data undefined?
        },
        error => {
          //this.submitted = false;
          this.errorMessage = 'Neispravan email ili lozinka.';
        });
  }

}

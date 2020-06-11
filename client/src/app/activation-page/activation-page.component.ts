import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: 'app-activation-page',
  templateUrl: './activation-page.component.html',
  styleUrls: ['./activation-page.component.css']
})
export class ActivationPageComponent implements OnInit {
  form: FormGroup;

	success: string;
	fail: string;

  errorRePassword: boolean;

  constructor(private messenger: AuthService, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      password: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
      rePassword: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])]
    });
  }

  ngOnInit(): void {
  }

  activate() {
    if (this.form.controls['rePassword'].value !== this.form.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    this.success = null;
    this.fail = null;

    let formObj = this.form.getRawValue();
    delete formObj['rePassword'];
  }

  focusField() {
    this.errorRePassword = false;
  }
}

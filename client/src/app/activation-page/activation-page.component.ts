import { Component, OnInit } from '@angular/core';
import { UserService } from '../service';
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

  constructor(private service: UserService, private formBuilder: FormBuilder) {
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

    this.service.activateProfile(formObj).subscribe(data => {
      console.log(data);
      if (data['status'] == 'ok') {
        this.success = 'Nalog uspešno aktiviran. Bićete prebačeni na naslovnu stranicu.';
        setTimeout(function () {
          window.location.href = "/";
        }, 3000);
      }
      else
        this.fail = 'Uneti podaci nisu u validni ili je nalog vec aktivan.';
    },
    (err) => { this.fail = 'Uneti podaci nisu u validni ili je nalog vec aktivan.' })
  }

  focusField() {
    this.errorRePassword = false;
  }
}

import { Component, OnInit } from '@angular/core';
import { UserService, Profile } from '../service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit {
	profileForm = this.fb.group({
				emailAddress: [{value: '', disabled: true}],
				password: ['', Validators.compose([Validators.minLength(8), Validators.maxLength(64)])],
				rePassword: ['', Validators.compose([Validators.minLength(8), Validators.maxLength(64)])],
		    firstName: ['', Validators.compose([Validators.required])],
		    lastName: ['', Validators.compose([Validators.required])],
		    address: ['', Validators.compose([Validators.required])],
		    city: ['', Validators.compose([Validators.required])],
		    country: ['', Validators.compose([Validators.required])],
		    contact: ['', Validators.compose([Validators.required, Validators.pattern("[+]?^[0-9]+")])],
		    jmbg: [{value: '', disabled: true}],
		  });

	close = false;
  errorRePassword: boolean;
  fail: boolean;

  constructor(private service: UserService, private fb: FormBuilder, public modal: NgbActiveModal) {

  }

  ngOnInit(): void {
  	this.service.getProfile().subscribe(result => {
  		result['rePassword'] = ''
  		this.profileForm.setValue(result);
  	})
  }

  updateProfile() {
  	if (this.profileForm.controls['rePassword'].value.length != 0 && this.profileForm.controls['rePassword'].value !== this.profileForm.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    if (this.profileForm.invalid) {
      this.fail = true;
      return;
    }

    this.fail =  false;

  	let payload = this.profileForm.getRawValue();
  	delete payload['rePassword'];

  	this.service.updateProfile(payload).subscribe(result => {
  		this.modal.dismiss('cancel click')
  	})
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }
}

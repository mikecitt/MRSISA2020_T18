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
				password: [''],
				rePassword: [''],
		    firstName: [''],
		    lastName: [''],
		    address: [''],
		    city: [''],
		    country: [''],
		    contact: [''],
		    jmbg: [{value: '', disabled: true}],
		  });

	close = false;

  constructor(private service: UserService, private fb: FormBuilder, public modal: NgbActiveModal) {

  }

  ngOnInit(): void {
  	this.service.getProfile().subscribe(result => {
  		result['rePassword'] = ''
  		this.profileForm.setValue(result);
  	})
  }

  updateProfile() {
  	//TODO: check
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

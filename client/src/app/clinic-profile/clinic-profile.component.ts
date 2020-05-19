import { Component, OnInit } from '@angular/core';
import { ClinicService, Clinic } from '../service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-clinic-profile',
  templateUrl: './clinic-profile.component.html',
  styleUrls: ['./clinic-profile.component.css']
})
export class ClinicProfileComponent implements OnInit {
  clinicProfileForm = this.fb.group({
				id: [{value: '', disabled: true}],
				name: [''],
				address: [''],
		    description: ['']
		  });

	close = false;

  constructor(private service: ClinicService, private fb: FormBuilder, public modal: NgbActiveModal) {

  }

  ngOnInit(): void {
  	this.service.getClinicProfile().subscribe(result => {
  		this.clinicProfileForm.setValue(result);
  	})
  }

  updateClinicProfile() {
  	let payload = this.clinicProfileForm.getRawValue();

  	this.service.updateClinicProfile(payload).subscribe(result => {
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

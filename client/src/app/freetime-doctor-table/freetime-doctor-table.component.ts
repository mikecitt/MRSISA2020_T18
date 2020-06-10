import { Component, OnInit, Input } from '@angular/core';
import { AppointmentService } from '../service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-freetime-doctor-table',
  templateUrl: './freetime-doctor-table.component.html',
  styleUrls: ['./freetime-doctor-table.component.css']
})
export class FreetimeDoctorTableComponent implements OnInit {
	@Input()
	freeDoctors = [];

	tableData = [];

	@Input()
	clinicId: number;

	@Input()
	examinationTypeId: number;

	showAlert: boolean = false;
	alertType: string;
	examinationTime: number;

	filter = new FormControl('');


  constructor(private service: AppointmentService) { 
  	this.filter.valueChanges.subscribe(val => {
  		this.tableData = this.freeDoctors.filter(entity => {
				const term = val.toLowerCase();
	    	return (entity['firstName'] as string).toLowerCase().includes(term)
	        || (entity['lastName'] as string).toLowerCase().includes(term);
	        // || (entity[''] as string).toLowerCase().includes(term);
  		})
  	})
  }

  ngOnInit(): void {
  	this.tableData = this.freeDoctors;
  }

  requestAppointment(payload, doctorId, event) {
  	payload['doctorId'] = doctorId;
  	payload['clinicId'] = this.clinicId;
  	payload['examinationTypeId'] = this.examinationTypeId;

  	this.showAlert = true;

  	this.service.requestAppointment(payload).subscribe(data => {
  		this.alertType = 'success';
  		this.examinationTime = payload.start;
      //Hack B|
      payload['marker'] = 1
  	},
  	err => {
  		this.alertType = 'danger';
  	})
  }

}

import { Component, OnInit, Input } from '@angular/core';
import { AppointmentService } from '../service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-clinic-details',
  templateUrl: './clinic-details.component.html',
  styleUrls: ['./clinic-details.component.css']
})
export class ClinicDetailsComponent implements OnInit {

	close = false;
	
	freeAppointments = [];
	alertType = 'success';
	showAlert = false;
  quick = false;

	booked;
  clinic;

  constructor(private service: AppointmentService, public modal: NgbActiveModal) { }

  ngOnInit(): void {
  	// this.service.getFreeAppointments()
  }

  setClinic(clinic) {
  	this.clinic = clinic
  	this.service.getFreeAppointments(clinic.id).subscribe(data =>
  	{
  		this.freeAppointments = data['data'];
  	})
  }

  closeAlert() {
  	console.log('alert')
  }

  book(appointment) {
  	this.service.bookAppointment(appointment.id).subscribe(data => {
  		if (data['status'] == 'ok') {
  			this.alertType = 'success';
  			this.showAlert = true;
  			this.booked = appointment;

  			this.freeAppointments = this.freeAppointments.filter(app => {
  				return app.id != appointment.id
  			})
  		}
  	}, error => {
  		this.alertType = 'danger';
  		this.showAlert = true;
  	})
  }

  setQuickAppointmentMode(quick) {
    this.quick = quick;
  }
}

import { Component, OnInit, Input } from '@angular/core';
import { AppointmentService, ClinicService } from '../service';
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

  rate = 3.61;

	booked;
  clinic;

  constructor(private service: AppointmentService, 
    public modal: NgbActiveModal,
    private clinicService: ClinicService) { }

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

  castToProc(num) {
    return num * 100;
  }

  rateClinic(payload): void {
    this.clinicService.gradeClinic({
      entityId: this.clinic.id,
      grade: payload
    }).subscribe(data => {
      console.log('ok');
    }, err => {
      console.error('rate error');
    })
  }
}

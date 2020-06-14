import { Component, OnInit, Inject } from '@angular/core';
import { SESSION_STORAGE, StorageService } from 'ngx-webstorage-service';
import { AppointmentService } from '../service';
import { Router } from '@angular/router';

const STORAGE_KEY = 'appointmentId';

@Component({
  selector: 'app-appointment-report',
  templateUrl: './appointment-report.component.html',
  styleUrls: ['./appointment-report.component.css']
})
export class AppointmentReportComponent implements OnInit {

  appointment;
  diagnosis;
  recipe;
  report;

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService,
              private appointmentService: AppointmentService,
              private router: Router) { }

  ngOnInit(): void {
    this.activeAppointment();
    this.appointmentService.getAppointment(this.storage.get(STORAGE_KEY)).subscribe(result => {
      if(result['data']) {
        this.appointment = result['data'][0];
      }
    },
    error => {
       this.router.navigate(['/patient-list']);
    });
  }

  activeAppointment() {
    this.appointmentService.canStartAppointment().subscribe(result => {
      console.log(result);
      if(result['description'] === "not started") {
        this.router.navigate(['/patients-list']);
      }
    });
  }

}

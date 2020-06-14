import { Component, OnInit, Inject } from '@angular/core';
import { SESSION_STORAGE, StorageService } from 'ngx-webstorage-service';
import { AppointmentService } from '../service';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

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

  dropdownListDiag = [];
  dropdownListRecip = [];
  dropdownSettings= {};

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService,
              private appointmentService: AppointmentService,
              private router: Router) { }

  ngOnInit(): void {
    this.activeAppointment();
    this.fillDropdowns();
    this.appointmentService.getAppointment(this.storage.get(STORAGE_KEY)).subscribe(result => {
      if(result['data']) {
        this.appointment = result['data'][0];
      }
    },
    error => {
       this.router.navigate(['/patient-list']);
    });
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }

  activeAppointment() {
    this.appointmentService.canStartAppointment().subscribe(result => {
      console.log(result);
      if(result['description'] === "not started") {
        this.router.navigate(['/patient-list']);
      }
    });
  }

  fillDropdowns() {
    this.appointmentService.getDiagnosis().subscribe(result => {
      this.dropdownListDiag = result['data'];
    });

    this.appointmentService.getDrugs().subscribe(result => {
      this.dropdownListRecip = result['data'];
    });
  }

  saveReport() {
    var form = {
      appointmentId: this.appointment.id,
      description: this.report,
      diagnosis: this.diagnosis,
      recipe: this.recipe
    };
    this.appointmentService.saveAppointment(form).subscribe(result => {

    });
  }


}

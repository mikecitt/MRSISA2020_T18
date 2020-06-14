import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren, Inject } from '@angular/core';
import { Subject, Observable, Subscription, of } from 'rxjs';
import { PatientService, Patient, AppointmentService, UserService } from '../service';
import { map, startWith } from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { SESSION_STORAGE, StorageService } from 'ngx-webstorage-service';

import { NgSortableHeader, SortDirection, SortEvent, compare } from '../sortable-table';

export type SortColumn = keyof Patient | '';

const STORAGE_KEY = 'appointmentId';

@Component({
  selector: 'app-patients-table',
  templateUrl: './patients-table.component.html',
  styleUrls: ['./patients-table.component.css']
})
export class PatientsTableComponent implements OnInit {

	//tableData: Patient[];
  data: Patient[];
  startedAppointment;
  btnMessage;

	@ViewChildren(NgSortableHeader) headers: QueryList<NgSortableHeader>;

	tableData: Patient[];
	filter = new FormControl('');

	onSort({column, direction}: SortEvent) {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting tableData
    if (direction === '' || column === '') {
      this.tableData = this.tableData;
    } else {
      this.tableData = [...this.tableData].sort((a, b) => {
        const res = compare(`${a[column]}`, `${b[column]}`);
        return direction === 'asc' ? res : -res;
      });
    }

  }

  constructor(private patientService: PatientService,
              private appointmentService: AppointmentService,
              private userService: UserService,
              private router: Router,
              @Inject(SESSION_STORAGE) private storage: StorageService) {
		this.filter.valueChanges.subscribe(val => {
  		this.tableData = this.data.filter(entity => {
				const term = val.toLowerCase();
	    	return entity.jmbg.toLowerCase().includes(term)
	        || entity.firstName.toLowerCase().includes(term)
	        || entity.lastName.toLowerCase().includes(term);
  		});
  	});
  }

  isDoctor() {
    let authority = this.userService.getRole();
      return authority === 'ROLE_DOCTOR';
  }

  ngOnInit(): void {
    this.activeAppointment();
  	this.patientService.getPatients().subscribe(result => {
  		this.data = result;
			this.tableData = result;
  	});
  }

  checkAppointmentStatus() {
    this.appointmentService.startAppointment(this.startedAppointment).subscribe(result => {
      this.storage.set(STORAGE_KEY, this.startedAppointment);
      this.router.navigate(['/appointment-rep']);
    });
  }

  activeAppointment() {
    this.appointmentService.canStartAppointment().subscribe(result => {
      console.log(result);
      this.btnMessage = "Pokreni pregled";
      if(result['description'] !== "not started") {
        if(result['description'] === "started") {
          this.btnMessage = "Nastavi pregled";
        }
        this.startedAppointment = result['data'][0];
      }
      else {
        this.storage.remove(STORAGE_KEY);
      }
    });
  }
}

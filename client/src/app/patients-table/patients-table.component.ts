import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Subject, Observable, Subscription, of } from 'rxjs';
import { PatientService, Patient } from '../service';
import { map, startWith } from 'rxjs/operators';
import { FormControl } from '@angular/forms';

import { NgSortableHeader, SortDirection, SortEvent, compare } from '../sortable-table';

export type SortColumn = keyof Patient | '';

@Component({
  selector: 'app-patients-table',
  templateUrl: './patients-table.component.html',
  styleUrls: ['./patients-table.component.css']
})
export class PatientsTableComponent implements OnInit {

	//tableData: Patient[];
  data: Patient[];

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

  constructor(private patientService: PatientService) {
		this.filter.valueChanges.subscribe(val => {
  		this.tableData = this.data.filter(entity => {
				const term = val.toLowerCase();
	    	return entity.jmbg.toLowerCase().includes(term)
	        || entity.firstName.toLowerCase().includes(term)
	        || entity.lastName.toLowerCase().includes(term);
  		})
  	})
  }

  ngOnInit(): void {
  	this.patientService.getPatients().subscribe(result => {
  		this.data = result;
			this.tableData = result;
  	})
  }
}

import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Subject, Observable, Subscription, of } from 'rxjs';
import { ClinicService, Clinic } from '../service';
import { map, startWith } from 'rxjs/operators';
import { FormControl } from '@angular/forms';

import { NgSortableHeader, SortDirection, SortEvent, compare } from '../sortable-table';

export type SortColumn = keyof Clinic | '';

export class ClinicSortableHeader {

}

@Component({
  selector: 'app-clinic-table',
  templateUrl: './clinic-table.component.html',
  styleUrls: ['./clinic-table.component.css']
})
export class ClinicTableComponent implements OnInit {

	//tableData: Clinic[];
	data: Clinic[];

	@ViewChildren(NgSortableHeader) headers: QueryList<NgSortableHeader>;

	tableData: Clinic[];
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

  constructor(private service: ClinicService) { 
  	this.filter.valueChanges.subscribe(val => {
  		this.tableData = this.data.filter(entity => {
				const term = val.toLowerCase();
	    	return entity.name.toLowerCase().includes(term)
	        || entity.address.toLowerCase().includes(term)
	        || entity.description.toLowerCase().includes(term);
  		})
  	})
  }

  ngOnInit(): void {
  	this.service.getClinics().subscribe(result => {
  		this.data = result;
  		this.tableData = result;
  	})
  }

}

import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Subject, Observable, Subscription, of } from 'rxjs';
import { PatientService, Patient } from '../service';
import { map, startWith } from 'rxjs/operators';
import { FormControl } from '@angular/forms';

export type SortColumn = keyof Patient | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: {[key: string]: SortDirection} = { 'asc': 'desc', 'desc': '', '': 'asc' };
const compare = (v1: string, v2: string) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

export interface SortEvent {
	column: SortColumn;
	direction: SortDirection;
}

@Directive({
  selector: 'th[sortable]',
  host: {
    '[class.asc]': 'direction === "asc"',
    '[class.desc]': 'direction === "desc"',
    '(click)': 'rotate()'
  }
})
export class NgbdSortableHeader {

  @Input() sortable: SortColumn = '';
  @Input() direction: SortDirection = '';
  @Output() sort = new EventEmitter<SortEvent>();

  rotate() {
    this.direction = rotate[this.direction];
    this.sort.emit({column: this.sortable, direction: this.direction});
  }
}

@Component({
  selector: 'app-patients-table',
  templateUrl: './patients-table.component.html',
  styleUrls: ['./patients-table.component.css']
})
export class PatientsTableComponent implements OnInit {

	//tableData: Patient[];
  data: Patient[];

	@ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;

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

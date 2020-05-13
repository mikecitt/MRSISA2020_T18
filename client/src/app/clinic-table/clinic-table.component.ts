import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Subject, Observable, Subscription, of } from 'rxjs';
import { ClinicService, Clinic } from '../service';
import { map, startWith } from 'rxjs/operators';
import { FormControl } from '@angular/forms';

export type SortColumn = keyof Clinic | '';
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
  selector: 'app-clinic-table',
  templateUrl: './clinic-table.component.html',
  styleUrls: ['./clinic-table.component.css']
})
export class ClinicTableComponent implements OnInit {

	//tableData: Clinic[];
	data: Clinic[];

	@ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;

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

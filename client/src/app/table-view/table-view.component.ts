import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { DoctorService } from '../doctor.service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface Doctor {
	firstName: string;
	lastName: string;
}

function search(text: string, pipe: PipeTransform, data: Doctor[]): Doctor[] {
  return data.filter(doctor => {
    const term = text.toLowerCase();
    return doctor.firstName.toLowerCase().includes(term)
        || doctor.lastName.toLowerCase().includes(term);
  });
}

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.css'],
  providers: [DecimalPipe]
})
export class TableViewComponent implements OnInit {
	@Input() endpoint: string;
	@Input() fieldsList: string[];

	data: Doctor[] = [];

	doctors: Observable<Doctor[]>;
	filter = new FormControl('');

	pipe: DecimalPipe;

  constructor(private doctorService: DoctorService, pipe: DecimalPipe) {
  	this.pipe = pipe;
  }

  ngOnInit(): void {
  	this.doctorService.getDoctors().subscribe(data => {
  		for (let e in data)
  			this.data.push({firstName: data[e].firstName, lastName: data[e].lastName});

  		// why not in constructor?
  			this.doctors = this.filter.valueChanges.pipe(
		  		startWith(''),
		  		map(text => search(text, this.pipe, this.data))
	  		)
  	})
  }

}

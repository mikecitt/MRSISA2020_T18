import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { DoctorService } from '../service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface Doctor {
	id: string;
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
  selector: 'app-doctors-table',
  templateUrl: './doctors-table.component.html',
  styleUrls: ['./doctors-table.component.css'],
  providers: [DecimalPipe]
})
export class DoctorsTableComponent implements OnInit {
	@Input() endpoint: string;
	@Input() fieldsList: string[];

  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

	data: Doctor[] = [];

	doctors: Observable<Doctor[]>;
	filter = new FormControl('');

	pipe: DecimalPipe;

  constructor(private doctorService: DoctorService, pipe: DecimalPipe) {
  	this.pipe = pipe;
  }

  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }

  ngOnInit(): void {
  	this.initTable();
    this.eventsSubscription = this.events.subscribe(() => this.initTable());
  }

  initTable(): void {
    this.data.length = 0;
    this.doctorService.getDoctors().subscribe(data => {
      for (let e in data)
        this.data.push({id: data[e].id, firstName: data[e].firstName, lastName: data[e].lastName});

      // why not in constructor?
        this.doctors = this.filter.valueChanges.pipe(
          startWith(''),
          map(text => search(text, this.pipe, this.data))
        )
    })
  }

	removeDoctor(id) {
		console.log(id);
		this.doctorService.removeDoctor(id).subscribe(data => {
			this.initTable();
		});
	}

}

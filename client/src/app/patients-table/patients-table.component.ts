import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { PatientService } from '../patient.service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface Patient {
	firstName: string;
	lastName: string;
	jmbg: string;
}

function search(text: string, data: Patient[]): Patient[] {
  return data.filter(patient => {
    const term = text.toLowerCase();2
    return patient.firstName.toLowerCase().includes(term)
        || patient.lastName.toLowerCase().includes(term)
        || patient.jmbg.toLowerCase().includes(term);
  });
}


@Component({
  selector: 'app-patients-table',
  templateUrl: './patients-table.component.html',
  styleUrls: ['./patients-table.component.css']
})
export class PatientsTableComponent implements OnInit {

  data: Patient[] = [];

	patients: Observable<Patient[]>;
	filter = new FormControl('');

  constructor(private patientService: PatientService) {
  }

  ngOnInit(): void {
  	this.patientService.getPatients().subscribe(data => {
  		for (let e in data['data']) 
  			this.data.push({firstName: data['data'][e].firstName, lastName: data['data'][e].lastName, jmbg: data['data'][e].jmbg});

  		// why not in constructor?
  			this.patients = this.filter.valueChanges.pipe(
		  		startWith(''),
		  		map(text => search(text, this.data))
	  		)
  	})
  }
}



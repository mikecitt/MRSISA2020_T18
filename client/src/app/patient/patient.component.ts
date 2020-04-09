import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../doctor.service';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {
	doctorsList = []

  constructor(private doctorService: DoctorService) { }

  ngOnInit(): void {
  	this.doctorService.getDoctors().subscribe(data => {
  		// iz nekog razloga se sad ne buni...
  		for (let e in data) this.doctorsList.push(data[e]);
  	})
  }

  sortName() {
  	this.doctorsList.sort((a, b) => (a.name > b.name ? 1 : -1))
  }

  sortLastname() {
  	this.doctorsList.sort((a, b) => (a.surname > b.surname ? 1 : -1))
  }

}

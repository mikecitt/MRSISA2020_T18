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

}

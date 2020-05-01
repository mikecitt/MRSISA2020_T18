import { Component, OnInit } from '@angular/core';
import { PatientService, MedicalReport } from '../patient.service';

@Component({
  selector: 'app-medical-record',
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.css']
})
export class MedicalRecordComponent implements OnInit {
	medicalRecord: MedicalReport[] = []

  constructor(private service: PatientService) { }

  ngOnInit(): void {
  	//console.log('call')
  	this.service.getMedicalRecord().subscribe(data => {
  		//console.log(data[''])
  		for (let r of data['data'])
  			this.medicalRecord.push(r)
  	})
  }

}

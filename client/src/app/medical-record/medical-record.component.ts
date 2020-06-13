import { Component, OnInit } from '@angular/core';
import { PatientService } from '../service';

@Component({
  selector: 'app-medical-record',
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.css']
})
export class MedicalRecordComponent implements OnInit {
	public medicalRecord;

  constructor(private service: PatientService) { }

  ngOnInit(): void {
  	//console.log('call')
  	this.service.getMedicalRecord().subscribe(data => {
  		//console.log(data[''])
      this.medicalRecord = data['data'][0];
  	})
  }

}

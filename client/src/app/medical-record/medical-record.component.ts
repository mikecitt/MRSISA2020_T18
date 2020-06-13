import { Component, OnInit, Input } from '@angular/core';
import { PatientService } from '../service';

@Component({
  selector: 'app-medical-record',
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.css']
})
export class MedicalRecordComponent implements OnInit {
	public medicalRecord;
  @Input() patientId: number;

  constructor(private service: PatientService) { }

  ngOnInit(): void {
  	//console.log('call')
    console.log(this.patientId)
  	this.service.getMedicalRecord(this.patientId).subscribe(data => {
  		//console.log(data[''])
      this.medicalRecord = data['data'][0];
  	})
  }

}

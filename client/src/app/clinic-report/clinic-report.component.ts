import { Component, OnInit } from '@angular/core';
import { ClinicService, DoctorService } from '../service';

interface Doctor {
	id: string;
	firstName: string;
	lastName: string;
  doctorGrade: number;
}

@Component({
  selector: 'app-clinic-report',
  templateUrl: './clinic-report.component.html',
  styleUrls: ['./clinic-report.component.css']
})

export class ClinicReportComponent implements OnInit {

  tableData: Doctor[] = [];
	clinicName: string = "";
	clinicAddress: string = "";
	clinicDescription : string = "";
	clinicGrade: number = 0;

  constructor(private clinicService: ClinicService, private doctorService: DoctorService) { }

  ngOnInit(): void {
		this.clinicService.getClinicProfile().subscribe(data => {
			this.clinicName = data.name;
			this.clinicGrade = data.clinicGrade;
			this.clinicDescription = data.description;
			this.clinicAddress = data.address;
			console.log(data);
		})
		this.doctorService.getDoctors().subscribe(data => {
			console.log(data);
      for (let e in data)
        this.tableData.push({id: data[e].id, firstName: data[e].firstName, lastName: data[e].lastName, doctorGrade: data[e].grade});
		})
  }

}

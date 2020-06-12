import { Component, OnInit } from '@angular/core';
import { ClinicService, DoctorService } from '../service';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";

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

	incomeForm = this.fb.group({
    dateFrom: ['', Validators.compose([Validators.required])],
		dateTo: ['', Validators.compose([Validators.required])]
  });

  tableData: Doctor[] = [];
	clinicName: string = "";
	clinicAddress: string = "";
	clinicDescription : string = "";
	clinicGrade: number = 0;
	clinicIncome: number = -1;
	errorMessage: string = null;

  constructor(private fb: FormBuilder, private clinicService: ClinicService, private doctorService: DoctorService) { }

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

	calculateIncome() {
		this.errorMessage = null;
		this.clinicIncome = -1;
		this.clinicService.getClinicIncome(this.incomeForm.getRawValue()).subscribe(data => {
			if(data['status'] == "ok")
				this.clinicIncome = data['data'][0];
			else
				this.errorMessage = "Uneli ste pogrešan format datuma.";
		},
		err => {
			this.errorMessage = "Uneli ste pogrešan format datuma.";
		})
	}

}

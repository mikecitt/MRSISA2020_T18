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
	choice = 1;
	clinicDescription : string = "";
	clinicGrade: number = 0;
	clinicIncome: number = -1;
	errorMessage: string = null;
	allChartData;

	currChartData;

  constructor(private fb: FormBuilder, private clinicService: ClinicService, private doctorService: DoctorService) { }

	public lineChartLabels = [''];
	public lineChartData = [{data: [''], label: 'Broj pregleda'}];
	public lineChartType = 'line';
	public lineChartOptions = {
		responsive: true,
		maintainAspectRatio: false,
		title: {
            display: true,
            text: 'Grafik broja pregleda'
        },
		legend: {
			display: false
		},
    scales: {
        yAxes: [{
            display: true,
            ticks: {
                suggestedMin: 0,    // minimum will be 0, unless there is a lower value.
                // OR //
                beginAtZero: true,   // minimum value will be 0.
								stepSize: 1
            }
        }]
    }
};

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
		this.clinicService.getClinicChartData().subscribe(data => {
			this.allChartData = data['data'];
			this.preview(2);
		})
  }

	preview(i) {
		this.lineChartLabels = this.allChartData[i][0];
		this.lineChartData = [
				{data: this.allChartData[i][1], label: 'Broj pregleda'}
			];
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

import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { ExaminationTypeService, ClinicService } from '../service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-search-examination',
  templateUrl: './search-examination.component.html',
  styleUrls: ['./search-examination.component.css']
})
export class SearchExaminationComponent implements OnInit {
	date = new FormControl('');
	examinationTypeId = new FormControl('');
	address = new FormControl('');
	grade = new FormControl('');
	examinationTypes = [];

	@Output()
  updateView = new EventEmitter();

  constructor(private examinationTypeService: ExaminationTypeService,
  						private clinicService: ClinicService) { }

  ngOnInit(): void {
  	this.examinationTypeService.getExaminationTypes().subscribe(data => {
  		//TODO: FIX! Access structure
  		this.examinationTypes = data as [];
  	})
  }

  search(): void {
  	let payload = {};
  	payload['date'] = this.date.value;
  	payload['examinationTypeId'] = this.examinationTypeId.value;

  	if (this.address.value)
  		payload['address'] = this.address.value;

  	//TODO: ocena

  	this.clinicService.getExaminationClinics(payload).subscribe(result => {
  		this.updateView.emit({result: result['data'], extended: true});
  	});
  }

  clear(): void {
  	this.clinicService.getClinics().subscribe(result => {
  		//TODO: FIX! Access structure
  		this.updateView.emit({result: result, extended: false});
  		this.date.reset();
	  	this.examinationTypeId.reset();
	  	this.address.reset();
  	})
  }

}

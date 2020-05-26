import { Component, OnInit, Input } from '@angular/core';
import { ExaminationTypeService } from '../service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-examination-type-edit',
  templateUrl: './examination-type-edit.component.html',
  styleUrls: ['./examination-type-edit.component.css']
})
export class ExaminationTypeEditComponent implements OnInit {
  @Input()
  public id;

  examinationTypeEditForm = this.fb.group({
        id: ['', Validators.compose([Validators.required])],
		    name: ['', Validators.compose([Validators.required])],
		    price: ['', Validators.compose([Validators.required])],
		  });

  close = false;
  fail: boolean;

  constructor(private service: ExaminationTypeService, private fb: FormBuilder, public modal: NgbActiveModal) { }

  ngOnInit(): void {
    this.service.getExaminationType(this.id).subscribe(result => {
  		this.examinationTypeEditForm.setValue(result);
  	})
  }

  updateExaminationType() {
    if (this.examinationTypeEditForm.invalid) {
      this.fail = true;
      return;
    }

    this.fail =  false;

  	let payload = this.examinationTypeEditForm.getRawValue();

  	this.service.updateExaminationType(payload).subscribe(result => {
  		this.modal.dismiss('cancel click')
  	})
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }

}

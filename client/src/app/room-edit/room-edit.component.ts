import { Component, OnInit, Input } from '@angular/core';
import { RoomService, ToastService } from '../service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-room-edit',
  templateUrl: './room-edit.component.html',
  styleUrls: ['./room-edit.component.css']
})
export class RoomEditComponent implements OnInit {
  @Input()
  public id;

  roomEditForm = this.fb.group({
        id: ['', Validators.compose([Validators.required])],
		    roomNumber: ['', Validators.compose([Validators.required])],
		    type: ['', Validators.compose([Validators.required])],
        firstFreeDate: []
		  });

  close = false;
  fail: boolean;

  constructor(private service: RoomService,
              private fb: FormBuilder,
              public modal: NgbActiveModal,
              private toastService: ToastService) { }

  ngOnInit(): void {
    this.service.getRoom(this.id).subscribe(result => {
  		this.roomEditForm.setValue(result);
  	})
  }

  updateRoom() {
    if (this.roomEditForm.invalid) {
      this.fail = true;
      return;
    }

    this.fail =  false;

  	let payload = this.roomEditForm.getRawValue();

  	this.service.updateRoom(payload).subscribe(result => {
      this.toastService.show('Izmena uspešna.', { classname: 'bg-success text-light', delay: 3000 });
  		this.modal.dismiss('cancel click')
  	}, err => {
      this.modal.dismiss('cancel click')
      this.toastService.show('Greška prilikom izmene.', { classname: 'bg-danger text-light', delay: 3000 });
    })
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }
}

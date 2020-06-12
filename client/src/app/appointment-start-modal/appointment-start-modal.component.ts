import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-appointment-start-modal',
  templateUrl: './appointment-start-modal.component.html',
  styleUrls: ['./appointment-start-modal.component.css']
})
export class AppointmentStartModalComponent implements OnInit {
  close = false;
  message: string = null;
  errorMessage: string = null;

  constructor(public modal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }
}

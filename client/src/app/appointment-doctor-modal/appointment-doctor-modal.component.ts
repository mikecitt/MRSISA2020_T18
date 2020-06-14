import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";
import { ExaminationType, ExaminationTypeService, RoomService, DoctorService, AppointmentService, ToastService } from '../service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-appointment-doctor-modal',
  templateUrl: './appointment-doctor-modal.component.html',
  styleUrls: ['./appointment-doctor-modal.component.css']
})
export class AppointmentDoctorModalComponent implements OnInit {
  form = this.fb.group({
    datetime: ['', Validators.compose([Validators.required])],
    duration: ['', Validators.compose([Validators.required])],
    type: ['', Validators.compose([Validators.required])],
    roomType: ['', Validators.compose([Validators.required])]
  });

  //@Input() patientId: number;

  patientId = null;
  loaded:boolean = false;
  examinationTypes: ExaminationType[] = [];
  errorDate: boolean;
  errorType: boolean;
  errorRoomType: boolean;
  errorDuration: boolean;
  message:string = null;
  doctorFree = 'true';

  close = false;

  constructor(public modal: NgbActiveModal,
              private fb: FormBuilder,
              private examinationTypeService: ExaminationTypeService,
              private appointmentService: AppointmentService,
              private toastService: ToastService,
              private spinner: NgxSpinnerService) { }

  ngAfterViewInit(): void {
  }

  ngOnInit(): void {
    console.log(this.patientId);
    this.examinationTypeService.getDoctorsSpecialists().subscribe(result => {
      this.examinationTypes = result['data'];
    })
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }

  book() {
    let formData = this.form.getRawValue();
    this.spinner.show();

    console.log(formData);
    this.appointmentService.requestAppointmentByDoctor(formData, this.patientId).subscribe(result => {
      this.spinner.hide();
      this.toastService.show('Zakazivanje uspešno.', { classname: 'bg-success text-light', delay: 3000 });
      this.modal.dismiss('cancel click');
      console.log(result);
    }, err=>{
      this.spinner.hide();
      this.modal.dismiss('cancel click');
      this.toastService.show('Greška u unosu.', { classname: 'bg-alert text-light', delay: 3000 });

    });
  }

}

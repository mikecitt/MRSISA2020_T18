import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";
import { ExaminationType, ExaminationTypeService, RoomService, DoctorService } from '../service';

interface Room {
  roomNumber: number;
  id: number;
  type: string;
}

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
    room: new FormControl({value: null, disabled: true}, Validators.required)
  });

  //@Input() patientId: number;

  patientId = null;
  loaded:boolean = false;
  examinationTypes: ExaminationType[] = [];
  rooms: Room[] = [];
  errorDate: boolean;
  errorType: boolean;
  errorDuration: boolean;
  message:string = null;
  doctorFree = 'true';

  close = false;

  constructor(public modal: NgbActiveModal,
              private fb: FormBuilder,
              private examinationTypeService: ExaminationTypeService,
              private roomService: RoomService,
              private doctorService: DoctorService) { }

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

  search() {
    this.doctorFree = 'true';
    this.message = null;
    this.loaded = false;
    this.form.controls['type'].disable();
    this.doctors = [];
    this.roomService.getAvailableExaminationRooms(this.form.controls['datetime'].value, this.form.controls['duration'].value).subscribe(result => {
      this.rooms = result as Room[];
      if(this.rooms.length > 0) {
        this.loaded = true;
        this.form.controls['room'].enable();
      }
  	})

    this.doctorService.isDoctorAvailable(this.form.controls['datetime'].value, this.form.controls['duration'].value).subscribe(result => {
      this.doctorFree = result['description'];
    })
  }

  cancel() {
    this.loaded = false;
    this.rooms = [];
    this.doctorFree = 'true';
    this.form.controls['room'].disable();
    this.form.controls['datetime'].enable();
    this.form.controls['type'].enable();
    this.form.controls['duration'].enable();
    this.form.reset();
  }

  book() {
    let formData = this.form.getRawValue();
    formData.patientId = this.patientId;

    console.log(formData);



  }

}

import { Component, OnInit } from '@angular/core';
import { RoomService, AppointmentService, ExaminationTypeService, DoctorService, ExaminationType } from '../service';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";

interface Room {
  roomNumber: number;
  id: number;
  type: string;
}

interface Doctor {
	id: string;
	name: string;
}

@Component({
  selector: 'app-appointment-predef',
  templateUrl: './appointment-predef.component.html',
  styleUrls: ['./appointment-predef.component.css']
})
export class AppointmentPredefComponent implements OnInit {
  form = this.fb.group({
    datetime: ['', Validators.compose([Validators.required])],
    duration: ['', Validators.compose([Validators.required])],
    type: ['', Validators.compose([Validators.required])],
    room: new FormControl({value: null, disabled: true}, Validators.required),
    doctor: new FormControl({value: null, disabled: true}, Validators.required)
  });
  examinationTypes: ExaminationType[] = [];
  rooms: Room[] = [];
  doctors: Doctor[] = [];
  loaded1: boolean = false;
  loaded2: boolean = false;
  errorDate: boolean;
  errorType: boolean;
  errorDuration: boolean;
  message:string = null;

  constructor(private fb: FormBuilder, private appointmentService: AppointmentService, private examinationTypeService: ExaminationTypeService, private roomService: RoomService, private doctorService: DoctorService) { }

  ngOnInit(): void {
    this.examinationTypeService.getExaminationTypes().subscribe(result => {
      this.examinationTypes = result;
  	})
  }

  search() {
    this.message = null;
    this.loaded1 = false;
    this.loaded2 = false;
    this.form.controls['type'].disable();
    this.doctors = [];
    this.roomService.getAvailableExaminationRooms(this.form.controls['datetime'].value, this.form.controls['duration'].value).subscribe(result => {
      this.rooms = result as Room[];
      if(this.rooms.length > 0) {
        this.loaded1 = true;
        this.form.controls['room'].enable();
      }
  	})

    this.doctorService.getAvailableDoctors(this.form.controls['datetime'].value, this.form.controls['duration'].value, this.form.controls['type'].value).subscribe(result => {
      for (let e in result)
        this.doctors.push({id: result[e].id, name: result[e].firstName + ' ' + result[e].lastName});
      if (this.doctors.length > 0) {
        this.loaded2 = true;
        this.form.controls['doctor'].enable();
      }
  	})
  }

  add() {
    let payload = this.form.getRawValue();
    this.appointmentService.addPredefinedAppointment(payload).subscribe(result => {
      this.cancel();
      if(result['status'] == "ok") {
        this.message = "Uspešno ste dodali predefinisani termin pregleda.";
      }
    })
  }

  cancel() {
    this.loaded1 = false;
    this.loaded2 = false;
    this.form.controls['room'].disable();
    this.form.controls['doctor'].disable();
    this.form.controls['datetime'].enable();
    this.form.controls['type'].enable();
    this.form.controls['duration'].enable();
    this.rooms = [];
    this.doctors = [];
    this.message = null;
  }
}

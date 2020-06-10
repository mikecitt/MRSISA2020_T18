import { Component, OnInit } from '@angular/core';
import { RoomService, ExaminationTypeService, DoctorService, ExaminationType } from '../service';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";

interface Room {
  roomNumber: string;
  id: string;
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
  form: FormGroup;
  examinationTypes: ExaminationType[] = [];
  rooms: Room[] = [];
  doctors: Doctor[] = [];
  loaded1: boolean = false;
  loaded2: boolean = false;

  constructor(private examinationTypeService: ExaminationTypeService, private roomService: RoomService, private doctorService: DoctorService) { }

  ngOnInit(): void {
    this.examinationTypeService.getExaminationTypes().subscribe(result => {
      this.examinationTypes = result;
  	})
  }

  add() {
    this.roomService.getRooms().subscribe(result => {
      //console.log(result);
      this.rooms = result;
      this.loaded1 = true;
  	})

    this.doctorService.getDoctors().subscribe(result => {
      console.log(result);
      for (let e in result)
        this.doctors.push({id: result[e].jmbg, name: result[e].firstName + ' ' + result[e].lastName});
      this.loaded2 = true;
  	})
  }
}

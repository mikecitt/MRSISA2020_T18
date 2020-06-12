import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs';
import { AppointmentService, RoomService } from '../service';

@Component({
  selector: 'app-approvement',
  providers: [DatePipe],
  templateUrl: './approvement.component.html',
  styleUrls: ['./approvement.component.css']
})
export class ApprovementComponent implements OnInit {

  dataTableSubscription: Subscription;
  requests: any[];
  rooms: any[];
  message = null;

  accept;
  room;
  selected;

  constructor(private appointmentService:AppointmentService, private datePipe:DatePipe,
              private roomService:RoomService) { }

  ngOnInit(): void {
    this.requests = [];
    this.appointmentService.getAppointmentRequests().subscribe(data => {
      console.log(data['data']);
      for(let elem of data['data']) {
        this.requests.push({
          id: elem.id,
          datetime: elem.datetime,
          duration: elem.duration,
          type: elem.type.name,
          doctor: elem.doctor
        });
      }
    });
  }

  getAvailableRooms(datetime, duration, selection) {
    this.selected = selection;
    datetime = this.datePipe.transform(new Date(datetime),"yyyy-MM-dd'T'HH:mm")
    this.roomService.getAvailableExaminationRooms(datetime, duration * 60).subscribe(data => {
      this.rooms = [];
      for(let i in data) {
        this.rooms.push(data[i]);
      }
    });
  }

  processApproval() {
    var form = {
      appointmentId: this.selected.id,
      roomNumber: this.room,
      approved: this.accept
    };

    this.appointmentService.solveRequest(form).subscribe(data => {
      var index = this.requests.indexOf(this.selected);

      if(index !== -1)
        this.requests.splice(index, 1);

      this.message = 'Zahtev je obrađen';
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AppointmentService, RoomService } from '../service';

@Component({
  selector: 'app-approvement',
  providers: [DatePipe],
  templateUrl: './approvement.component.html',
  styleUrls: ['./approvement.component.css']
})
export class ApprovementComponent implements OnInit {

  requests: any[];
  rooms: any[];

  accept;
  room;
  selectedId;

  constructor(private appointmentService:AppointmentService, private datePipe:DatePipe,
              private roomService:RoomService) { }

  ngOnInit(): void {
    this.requests = [];
    this.appointmentService.getAppointmentRequests().subscribe(data => {
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

  getAvailableRooms(datetime, duration, id) {
    this.selectedId = id;
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
      id: this.selectedId,
      roomNumber: this.room,
      approved: this.accept
    };
  }

}

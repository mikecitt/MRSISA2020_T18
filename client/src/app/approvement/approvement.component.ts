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

  getAvailableRooms(selection) {
    this.selected = selection;
    var datetime = this.datePipe.transform(new Date(selection.datetime),"yyyy-MM-dd'T'HH:mm")
    this.roomService.getAvailableExaminationRooms(datetime, selection.duration).subscribe(data => {
      this.rooms = [];
      for(let i in data) {
        this.rooms.push(data[i]);
      }
    });
  }

  disapprove(selection) {
    this.sendProcess(false, selection);
  }

  processApproval() {
    this.sendProcess(true, this.selected);
  }

  sendProcess(accept, selection) {
    var form = {
        appointmentId: selection.id,
        roomNumber: this.room,
        approved: accept
    };
    this.appointmentService.solveRequest(form).subscribe(data => {
      var index = this.requests.indexOf(selection);

      if(index !== -1)
        this.requests.splice(index, 1);

      this.rooms = null;
      this.message = 'Zahtev je obrađen';
    });
  }
}

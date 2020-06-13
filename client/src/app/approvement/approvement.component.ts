import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs';
import { AppointmentService, RoomService } from '../service';
import { NgxSpinnerService } from 'ngx-spinner';

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
  newDateRooms: any[];
  message = null;
  alert = false;

  accept;
  room;
  selected;

  constructor(private appointmentService:AppointmentService, private datePipe:DatePipe,
              private roomService:RoomService,
              private spinner: NgxSpinnerService) { }

  ngAfterViewInit(): void {
  }

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
    this.rooms = null;
    this.newDateRooms = null;
    this.room = null;
    this.selected = selection;
    var datetime = this.datePipe.transform(new Date(selection.datetime),"yyyy-MM-dd'T'HH:mm")
    this.roomService.getAvailableExaminationRooms(datetime, selection.duration).subscribe(data => {
      console.log(data);
      if((data as any[]).length === 0) {
        this.roomService.getNewFreeRooms(selection.id).subscribe(data => {
          this.newDateRooms = [];
          for(let elem of data['data']) {
            this.newDateRooms.push(elem);
          }
        });
      } else {
        this.rooms = [];
        for(let i in data) {
          this.rooms.push(data[i]);
        }
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
    this.spinner.show();
    var roomNumber = this.room ? this.room.roomNumber : null;
    var form = {
        appointmentId: selection.id,
        roomNumber: roomNumber,
        approved: accept
    };

    if(this.room != null && this.room.firstFreeDate)
      form['newDate'] = this.room.firstFreeDate;

    this.appointmentService.solveRequest(form).subscribe(data => {
      var index = this.requests.indexOf(selection);

      if(index !== -1)
        this.requests.splice(index, 1);

      this.rooms = null;
      this.newDateRooms = null;
      this.room = undefined;
      this.message = 'Zahtev je obrađen';
      this.spinner.hide();
    },
    error => {
      this.alert = true;
      if(error.error['description'] === 'taken') {
        this.message = 'Sala je već zauzeta';
      }
      else {
        this.message = 'Došlo je do greške';
      }
      this.spinner.hide();
    });
  }
}

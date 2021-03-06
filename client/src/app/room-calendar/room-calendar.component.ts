import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FullCalendarComponent } from '@fullcalendar/angular';
import { RoomService } from '../service';

import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';
import srLocale from '@fullcalendar/core/locales/sr';
import bootstrapPlugin from '@fullcalendar/bootstrap';

@Component({
  selector: 'app-room-calendar',
  templateUrl: './room-calendar.component.html',
  styleUrls: ['./room-calendar.component.css']
})
export class RoomCalendarComponent implements OnInit {

  @ViewChild('fc') calendar: FullCalendarComponent;

  close = false;

  calendarPlugins = [
    dayGridPlugin,
    timeGridPlugin,
    momentPlugin,
    bootstrapPlugin,
    interactionPlugin
  ];
  locales = [srLocale];
  calendarEvents: any[] = [];
  grayDays: any[];

  selectionEnabled = false;
  dateSelection = null;

  id = 1; // test

  constructor(private service: RoomService, public modal: NgbActiveModal) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.service.getEvents(this.id).subscribe(data => {
      this.calendarEvents = data['data'];
    });
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }

}

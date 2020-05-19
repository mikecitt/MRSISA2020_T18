import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import momentPlugin from '@fullcalendar/moment';
import srLocale from '@fullcalendar/core/locales/sr';
import bootstrapPlugin from '@fullcalendar/bootstrap';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  calendarPlugins = [dayGridPlugin, momentPlugin, bootstrapPlugin];
  locales = [srLocale];
  calendarEvents = [];

  constructor() {
    console.log(momentPlugin, dayGridPlugin)
  }

  ngOnInit(): void {
    this.addEvent(); // temporary
  }

  addEvent() {
    this.calendarEvents = this.calendarEvents.concat( // creates a new array!
      { title: 'event 2', date: '2020-05-02' }
    );
  }

}

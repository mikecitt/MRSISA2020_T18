import { Component, OnInit, Input } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import momentPlugin from '@fullcalendar/moment';
import srLocale from '@fullcalendar/core/locales/sr';
import bootstrapPlugin from '@fullcalendar/bootstrap';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  calendarPlugins = [dayGridPlugin, timeGridPlugin, momentPlugin, bootstrapPlugin];
  locales = [srLocale];
  @Input() calendarEvents = [];
  @Input() grayDays = [];

  constructor() {}

  ngOnInit(): void {
  }

  addEvent() {
    // { title: 'event 2', date: '2020-05-02' } format of events for calendar
  }

}

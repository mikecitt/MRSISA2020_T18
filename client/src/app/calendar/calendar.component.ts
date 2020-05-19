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
  @Input() grayDays = {};

  constructor() {}

  ngOnInit(): void {
  }

  renderDaysOff(dayRenderInfo) {
    var currentCellDate = new Date(dayRenderInfo.date);
    var currentStart = new Date(dayRenderInfo.view.currentStart);
    var currentEnd = new Date(dayRenderInfo.view.currentEnd);

    if (currentCellDate >= currentEnd || currentCellDate < currentStart){
      dayRenderInfo.el.classList.add("out-of-range");
    }

    var grayStart = new Date(this.grayDays['startDate']);
    var grayEnd = new Date(this.grayDays['endDate']);
    console.log(grayStart, currentCellDate)
    if(currentCellDate >= grayStart && currentCellDate <= grayEnd) {
      dayRenderInfo.el.classList.add("days-off");
    }
  }

  addEvent() {
    // { title: 'event 2', date: '2020-05-02' } format of events for calendar
  }

}

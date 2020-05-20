import { Component, OnInit, Input } from '@angular/core';
import { DateIntervalComponent } from '../date-interval/date-interval.component';
import { UserService } from '../service';

import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';
import srLocale from '@fullcalendar/core/locales/sr';
import bootstrapPlugin from '@fullcalendar/bootstrap';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  calendarPlugins = [
    dayGridPlugin,
    timeGridPlugin,
    momentPlugin,
    bootstrapPlugin,
    interactionPlugin
  ];
  locales = [srLocale];
  @Input() calendarEvents;
  @Input() grayDays: any;

  selectionEnabled = false;
  dateSelection = null;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    let authorities = JSON.stringify(this.userService.currentUser.authorities);
    if(authorities.search('ROLE_DOCTOR') !== -1 ||
       authorities.search('ROLE_NURSE') !== -1) {
      this.selectionEnabled = true;
    }
  }

  renderDaysOff(dayRenderInfo) {
    var currentCellDate = new Date(dayRenderInfo.date);
    var currentStart = new Date(dayRenderInfo.view.currentStart);
    var currentEnd = new Date(dayRenderInfo.view.currentEnd);

    if (currentCellDate >= currentEnd || currentCellDate < currentStart){
      dayRenderInfo.el.classList.add("out-of-range");
    }

    for(let grayDay of this.grayDays) {
      var grayStart = new Date(grayDay['startDate']);
      var grayEnd = new Date(grayDay['endDate']);

      if(currentCellDate >= grayStart && currentCellDate <= grayEnd) {
        dayRenderInfo.el.classList.add("days-off");
      }
    }
  }

  setSelection(selectionInfo) {
    console.log(selectionInfo);
    this.dateSelection = selectionInfo;
  }

  inRange(selectInfo): boolean {
    console.log(selectInfo);
    return true;
  }

  addEvent() {
    // { title: 'event 2', date: '2020-05-02' } format of events for calendar
  }

}

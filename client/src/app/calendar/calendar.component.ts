import { Component, OnInit, ViewChild } from '@angular/core';
import { DateIntervalComponent } from '../date-interval/date-interval.component';
import { AppointmentStartModalComponent } from '../appointment-start-modal/appointment-start-modal.component';
import { UserService, MedicalService, AppointmentService } from '../service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FullCalendarComponent } from '@fullcalendar/angular';

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

  @ViewChild('fc') calendar: FullCalendarComponent;

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

  constructor(private userService: UserService,
              private medicalService: MedicalService,
              private modalService: NgbModal,
              private appointmentService: AppointmentService) {
  }

  ngOnInit(): void {
    this.loadData();
    this.selectionEnabled = false;
    this.isMedical();
  }

  isDoctor() {
    let authority = this.userService.getRole();
    if(authority === 'ROLE_DOCTOR') {
      this.selectionEnabled = true;
    }
    return this.selectionEnabled;
  }

  startAppointment() {
    this.appointmentService.startAppointment().subscribe(result => {
      const modalRef = this.modalService.open(AppointmentStartModalComponent);
      if(result.description == "started")
        modalRef.componentInstance.message = "Uspesno ste pokrenuli pregled.";
      else
        modalRef.componentInstance.errorMessage = "Nema trenutnih pregleda za pokretanje.";
    })
  }

  isMedical() {
    let authority = this.userService.getRole();
    if(authority === 'ROLE_DOCTOR' || authority === 'ROLE_NURSE') {
      this.selectionEnabled = true;
    }
    return this.selectionEnabled;
  }

  loadData() {
    var role = this.userService.getRole();
    if(role === 'ROLE_DOCTOR' || role === 'ROLE_NURSE' ) {
      this.medicalService.getTimeOffs().subscribe(data => {
        this.grayDays = data['data'];
      });

      this.medicalService.getEvents().subscribe(data => {
        this.calendarEvents = data['events'];
      });
    }
    else {
      this.grayDays = [];
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

  showTimeOffModal(dateSelection=null) {
    const modalRef = this.modalService.open(DateIntervalComponent, {
      size: 'md',
      windowClass: 'modal-holder',
      centered: true,
      backdrop: false
    });
    modalRef.componentInstance.selection = dateSelection;
    modalRef.componentInstance.uploaded.subscribe((event) => {
      this.changeSelection(event);
    });
  }

  changeSelection(event) {
    console.log(event);
    this.calendar.getApi().select(event.start, event.end);
  }

  setSelection(selectionInfo) {
    //console.log(selectionInfo);
    this.dateSelection = selectionInfo;
    if(!this.modalService.hasOpenModals())
      this.showTimeOffModal(this.dateSelection);
  }

  addEvent() {
    // { title: 'event 2', date: '2020-05-02' } format of events for calendar
  }

}

import { Component, OnInit, Input, Injectable, ViewChild, AfterViewInit, EventEmitter, Output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MedicalService, UserService } from '../service';
import { NgbActiveModal, NgbDatepickerI18n, NgbDateStruct, NgbDatepicker } from '@ng-bootstrap/ng-bootstrap';

const I18N_VALUES = {
  'sr': {
    weekdays: ['pon.', 'uto.', 'sre.', 'čet.', 'pet.', 'sub.', 'ned.'],
    months: ['jan', 'feb', 'mar', 'apr', 'maj', 'jun', 'jul', 'avg', 'sept', 'okt', 'nov', 'dec'],
  }
  // other languages you would support
};

@Injectable()
export class I18n {
  language = 'sr';
}

@Injectable()
export class CustomDatepickerI18n extends NgbDatepickerI18n {

  constructor(private _i18n: I18n) {
    super();
  }

  getWeekdayShortName(weekday: number): string {
    return I18N_VALUES[this._i18n.language].weekdays[weekday - 1];
  }

  getMonthShortName(month: number): string {
    return I18N_VALUES[this._i18n.language].months[month - 1];
  }

  getMonthFullName(month: number): string {
    return this.getMonthShortName(month);
  }

  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}-${date.month}-${date.year}`;
  }
}

@Component({
  selector: 'app-date-interval',
  providers: [DatePipe, I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}],
  templateUrl: './date-interval.component.html',
  styleUrls: ['./date-interval.component.css']
})
export class DateIntervalComponent implements OnInit, AfterViewInit {

  @Input() selection:any;

  @ViewChild('d1') datepicker1: NgbDatepicker;
  @ViewChild('d2') datepicker2: NgbDatepicker;

  @Output()
  uploaded = new EventEmitter<any>();

  start:Date;
  end:Date;
  startDate:NgbDateStruct;
  endDate:NgbDateStruct;

  constructor(private datepipe: DatePipe, private medicalService:MedicalService,
              private userService:UserService, public modal: NgbActiveModal) { }

  toNgbDateStruct(value) {
    if(value) {
      return {
        year: value.getFullYear(),
        month: value.getMonth() + 1,
        day: value.getDate()
      };
    }
    return null;
  }

  toBasicDate(struct) {
    if(struct) {
      return new Date(`${struct.year}-${struct.month}-${struct.day}`);
    }
    return null;
  }

  ngOnInit(): void {
    if(this.selection) {
      this.start = this.selection['start'];
      this.end = this.selection['end'];
      this.end.setMinutes(this.end.getMinutes() - 1)
      this.startDate = this.toNgbDateStruct(this.start);
      this.endDate = this.toNgbDateStruct(this.end);
    }
    else {
      var date = this.toNgbDateStruct(new Date());
      this.startDate = date;
      this.endDate = date;
    }
  }

  emitDate() {
    this.uploaded.emit({
      start: this.toBasicDate(this.startDate),
      end: this.toBasicDate(this.endDate)
    });
  }

  ngAfterViewInit() {
    var date1 = { year: this.startDate.year, month: this.startDate.month }
    var date2 = { year: this.endDate.year, month: this.endDate.month }
    this.datepicker1.navigateTo(date1);
    this.datepicker2.navigateTo(date2);
  }

  postTimeOff() {
    var body = {
      startDate: this.toBasicDate(this.startDate),
      endDate: this.toBasicDate(this.endDate)
    };
    console.log(body);
    this.medicalService.sendVacationRequest(body).subscribe(data => {
      window.location.reload();
    })
  }
}

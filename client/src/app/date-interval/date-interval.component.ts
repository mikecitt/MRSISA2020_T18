import { Component, OnInit, Input, Injectable } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MedicalService, UserService } from '../service';
import { NgbActiveModal, NgbDatepickerI18n, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

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
export class DateIntervalComponent implements OnInit {

  @Input() selection:any;

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
      this.startDate = null;
      this.endDate = null;
    }
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

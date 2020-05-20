import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MedicalService, UserService } from '../service';

@Component({
  selector: 'app-date-interval',
  providers: [DatePipe],
  templateUrl: './date-interval.component.html',
  styleUrls: ['./date-interval.component.css']
})
export class DateIntervalComponent implements OnInit, OnChanges {

  @Input() selection:any;

  start:string;
  end:string;
  startDate:string;
  endDate:string;

  constructor(private datepipe: DatePipe, private medicalService:MedicalService,
              private userService:UserService) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes) {
    if(changes['selection']) {
      if(this.selection) {
        this.start = this.datepipe.transform(this.selection['start'], 'dd.MM.yyyy.');
        this.end = this.datepipe.transform(this.selection['end'], 'dd.MM.yyyy.');
        this.startDate = this.selection['start'];
        this.endDate = this.selection['end'];
      }
      else {
        this.start = '';
        this.end = '';
      }
    }
  }

  postTimeOff() {
    var body = {
      startDate: this.startDate,
      endDate: this.endDate
    };
    console.log(body);
    var authority = this.userService.currentUser.authorities[0].authority;
    this.medicalService.sendVacationRequest(authority, body).subscribe(data => {
      window.location.reload();
    })
  }
}

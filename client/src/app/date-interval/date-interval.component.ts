import { Component, OnInit, Input } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MedicalService, UserService } from '../service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-date-interval',
  providers: [DatePipe],
  templateUrl: './date-interval.component.html',
  styleUrls: ['./date-interval.component.css']
})
export class DateIntervalComponent implements OnInit {

  @Input() selection:any;

  start:string;
  end:string;
  startDate:Date;
  endDate:Date;

  constructor(private datepipe: DatePipe, private medicalService:MedicalService,
              private userService:UserService, public modal: NgbActiveModal) { }

  ngOnInit(): void {
    if(this.selection) {
      this.startDate = this.selection['start'];
      this.endDate = this.selection['end'];
      this.endDate.setMinutes(this.endDate.getMinutes() - 1)
      this.start = this.datepipe.transform(this.startDate, 'dd.MM.yyyy.');
      this.end = this.datepipe.transform(this.endDate, 'dd.MM.yyyy.');
    }
    else {
      this.start = '';
      this.end = '';
    }
  }

  postTimeOff() {
    var body = {
      startDate: this.startDate,
      endDate: this.endDate
    };
    console.log(body);
    this.medicalService.sendVacationRequest(body).subscribe(data => {
      window.location.reload();
    })
  }
}

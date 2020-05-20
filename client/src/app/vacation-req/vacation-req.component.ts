import { Component, OnInit } from '@angular/core';
import { TimeoffService } from '../service';
import { DatePipe } from '@angular/common';

interface TimeOff {
  id: string;
  jmbg: string;
  firstName: string;
  lastName: string;
  startDate: string;
  endDate: string;
}

@Component({
  selector: 'app-vacation-req',
  providers: [DatePipe],
  templateUrl: './vacation-req.component.html',
  styleUrls: ['./vacation-req.component.css']
})
export class VacationReqComponent implements OnInit {

  data: TimeOff[] = [];

  constructor(private timeOffService: TimeoffService, public datepipe: DatePipe) { }

  ngOnInit(): void {
    this.loadTimeOffs();
  }

  loadTimeOffs() {
    this.timeOffService.getTimeOffs().subscribe(data => {
      for (let e in data)
        this.data.push({id: '', jmbg: data[e].jmbg, firstName: data[e].firstName, lastName: data[e].lastName, startDate: this.datepipe.transform(data[e].startDate, 'dd.MM.yyyy.'), endDate: this.datepipe.transform(data[e].endDate, 'dd.MM.yyyy.')});
      console.log(data);
    });
  }
}

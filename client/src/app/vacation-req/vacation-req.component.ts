import { Component, OnInit } from '@angular/core';
import { TimeoffService, CcadminService } from '../service';
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
  id:string = '';
  jmbg:string = '';
  accept:boolean = null;
  message:string = '';

  constructor(private timeOffService: TimeoffService, private adminService: CcadminService, public datepipe: DatePipe) { }

  ngOnInit(): void {
    this.loadTimeOffs();
  }

  rowSelected(req) {
    this.id = req.id;
    this.jmbg = req.jmbg;
  }

  loadTimeOffs() {
    this.timeOffService.getTimeOffs().subscribe(data => {
      for (let e in data)
        this.data.push({id: data[e].id, jmbg: data[e].jmbg, firstName: data[e].firstName, lastName: data[e].lastName, startDate: this.datepipe.transform(data[e].startDate, 'dd.MM.yyyy.'), endDate: this.datepipe.transform(data[e].endDate, 'dd.MM.yyyy.')});
      console.log(data);
    });
  }

  processVacation() {
    var acceptance = {
      userJmbg: this.jmbg,
      accept: this.accept,
      message: this.message
    }
    var accept = {
      id: this.id,
      active: this.accept
    }

    this.jmbg = '';
    this.accept = false;
    this.message = '';
    this.timeOffService.postAcceptance(acceptance).subscribe(data => {
      if(data['status'] !== 'error') {
        window.location.reload();
      }
    });

    this.timeOffService.solveTimeOff(accept).subscribe(data => {
      console.log(data);
    });
  }
}

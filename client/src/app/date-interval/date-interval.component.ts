import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { DatePipe } from '@angular/common';

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

  constructor(public datepipe: DatePipe) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes) {
    if(changes['selection']) {
      if(this.selection) {
        this.start = this.datepipe.transform(this.selection['start'], 'dd.MM.yyyy.');
        this.end = this.datepipe.transform(this.selection['end'], 'dd.MM.yyyy.');
      }
      else {
        this.start = '';
        this.end = '';
      }
    }
  }
}

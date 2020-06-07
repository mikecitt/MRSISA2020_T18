import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { NurseService } from '../service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface Nurse {
	id: string;
	firstName: string;
	lastName: string;
}

function search(text: string, pipe: PipeTransform, data: Nurse[]): Nurse[] {
  return data.filter(nurse => {
    const term = text.toLowerCase();
    return nurse.firstName.toLowerCase().includes(term)
        || nurse.lastName.toLowerCase().includes(term);
  });
}
@Component({
  selector: 'app-nurses-table',
  templateUrl: './nurses-table.component.html',
  styleUrls: ['./nurses-table.component.css'],
  providers: [DecimalPipe]
})
export class NursesTableComponent implements OnInit {

  @Input() endpoint: string;
  @Input() fieldsList: string[];

  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

  data: Nurse[] = [];
  tableData: Nurse[] = [];

  doctors: Observable<Nurse[]>;
  filter = new FormControl('');

  pipe: DecimalPipe;

  constructor(private nurseService: NurseService, pipe: DecimalPipe) {
    this.pipe = pipe;

    this.filter.valueChanges.subscribe(val => {
      this.tableData = this.data.filter(entity => {
        const term = val.toLowerCase();
        return entity.firstName.toLowerCase().includes(term)
          || entity.lastName.toLowerCase().includes(term);
      })
    })
  }

  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }

  ngOnInit(): void {
    this.initTable();
    this.eventsSubscription = this.events.subscribe(() => this.initTable());
  }

  initTable(): void {
    this.data.length = 0;
    this.tableData.length = 0;
    this.filter.setValue('');
    this.nurseService.getNurses().subscribe(data => {
      for (let e in data)
        this.data.push({id: data[e].id, firstName: data[e].firstName, lastName: data[e].lastName});
        this.tableData = this.data
    })
  }

  removeNurse(id) {
    console.log(id);
    this.nurseService.removeNurse(id).subscribe(data => {
      this.initTable();
    });
  }
}

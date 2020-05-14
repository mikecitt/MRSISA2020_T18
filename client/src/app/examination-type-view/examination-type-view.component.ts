import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { ExaminationTypeService } from '../service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface ExaminationType {
  id: string;
  name: string;
  price: string;
}

function search(text:string, pipe: PipeTransform, data: ExaminationType[]): ExaminationType[] {
  return data.filter(examinationType => {
    const term = text.toLowerCase();
    return examinationType.name.toLowerCase().includes(term);
  });
}

@Component({
  selector: 'app-examination-type-view',
  templateUrl: './examination-type-view.component.html',
  styleUrls: ['./examination-type-view.component.css'],
  providers: [DecimalPipe]
})
export class ExaminationTypeViewComponent implements OnInit {
  @Input() endpoint: string;
  @Input() fieldsList: string[];

  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

  data: ExaminationType[] = [];

  examinationTypes: Observable<ExaminationType[]>;
  filter = new FormControl('');

  pipe: DecimalPipe;

  constructor(private examinationTypeService: ExaminationTypeService,
    pipe: DecimalPipe) {
      this.pipe = pipe;
    }

  ngOnInit(): void {
    this.eventsSubscription = this.events.subscribe(() => this.initTable());
    this.initTable();
  }

  initTable(): void {
    this.data.length = 0;
    this.examinationTypeService.getExaminationTypes().subscribe(data => {
      for (let e in data)
        this.data.push({id: data[e].id, name: data[e].name, price: data[e].price});

        this.examinationTypes = this.filter.valueChanges.pipe(
          startWith(''),
          map(text => search(text, this.pipe, this.data))
        )
    })
  }

  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }

  removeExaminationType(id) {
    this.examinationTypeService.removeExaminationType(id).subscribe(data => {
      this.initTable();
    });
  }
}

import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { ExaminationTypeService } from '../examination-type.service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface ExaminationType {
  id: string;
  name: string;
  price: string;
}

function search(text:string, pipe: PipeTransform, data: ExaminationType[]): ExaminationType[] {
  return data.filter(examinationType => {
    const term = text.toLowerCase();2
    return examinationType.name.toLowerCase().includes(term)
        || examinationType.id.includes(term);
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

  data: ExaminationType[] = [];

  examinationTypes: Observable<ExaminationType[]>;
  filter = new FormControl('');

  pipe: DecimalPipe;

  constructor(private examinationTypeService: ExaminationTypeService,
    pipe: DecimalPipe) {
      this.pipe = pipe;
    }

  ngOnInit(): void {
    this.examinationTypeService.getExaminationTypes().subscribe(data => {
      for (let e in data)
        this.data.push({id: data[e].id, name: data[e].name, price: data[e].price});

        this.examinationTypes = this.filter.valueChanges.pipe(
          startWith(''),
          map(text => search(text, this.pipe, this.data))
        )
    })
  }
}

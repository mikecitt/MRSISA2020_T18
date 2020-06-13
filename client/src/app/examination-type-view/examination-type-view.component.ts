import { Component, OnInit, Input, PipeTransform, TemplateRef } from '@angular/core';
import { ExaminationTypeService, ToastService } from '../service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ExaminationTypeEditComponent } from '../examination-type-edit/examination-type-edit.component';

import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface ExaminationType {
  id: string;
  name: string;
  price: string;
}

@Component({
  selector: 'app-examination-type-view',
  templateUrl: './examination-type-view.component.html',
  styleUrls: ['./examination-type-view.component.css'],
  providers: [DecimalPipe]
})
export class ExaminationTypeViewComponent implements OnInit {
  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

  data: ExaminationType[] = [];
  tableData: ExaminationType[] = [];

  filter = new FormControl('');

  pipe: DecimalPipe;

  constructor(private examinationTypeService: ExaminationTypeService,
    pipe: DecimalPipe, private modalService: NgbModal,
    public toastService: ToastService) {
      this.pipe = pipe;

      this.filter.valueChanges.subscribe(val => {
        this.tableData = this.data.filter(entity => {
            const term = val.toLowerCase();
            return entity.name.toLowerCase().includes(term);
          })
      })
    }

  ngOnInit(): void {
    this.eventsSubscription = this.events.subscribe(() => this.initTable());
    this.initTable();
  }

  initTable(): void {
    this.data.length = 0;
    this.filter.setValue('');
    this.examinationTypeService.getExaminationTypes().subscribe(data => {
      for (let e in data)
        this.data.push({id: data[e].id, name: data[e].name, price: data[e].price});

        this.tableData = this.data;
    })
  }

  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }

  isTemplate(toast) { return toast.textOrTpl instanceof TemplateRef; }

  removeExaminationType(id) {
    this.examinationTypeService.removeExaminationType(id).subscribe(data => {
      this.initTable();
      this.toastService.show('Brisanje uspešno.', { classname: 'bg-success text-light', delay: 3000 });
    },
    err => {
      console.log(err);
      this.toastService.show('Nije moguće obrisati stavku.', { classname: 'bg-danger text-light', delay: 3000 });
    });
  }

  openExaminationTypeEdit(id): void {
    const modalRef = this.modalService.open(ExaminationTypeEditComponent);
    modalRef.componentInstance.id = id;

    modalRef.result.then((data) => {
    }, (reason) => {
      this.initTable(); // TODO: make better
    });
  }
}

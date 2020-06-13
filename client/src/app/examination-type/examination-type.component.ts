import { Component, OnInit } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { Subject } from 'rxjs';
import { ExaminationTypeService } from '../service';

@Component({
  selector: 'app-examination-type',
  templateUrl: './examination-type.component.html',
  styleUrls: ['./examination-type.component.css'],
  animations: [
    trigger('smoothCollapse', [
      state('initial', style({
        height:'0',
        overflow:'hidden',
        opacity:'0'
      })),
      state('final', style({
        overflow:'hidden',
        opacity:'1'
      })),
      transition('initial=>final', animate('750ms')),
      transition('final=>initial', animate('750ms'))
    ]),
  ]
})
export class ExaminationTypeComponent implements OnInit {

  eventsSubject: Subject<void> = new Subject<void>();

  public isCollapsed = true;

  name:string = '';
  price:string = '';
  errorMessage:string = null;

  constructor(private service:ExaminationTypeService) { }

  ngOnInit(): void {
  }

  addExaminationType() {
    this.errorMessage = null;
    var formData = {
      "name"  : this.name,
      "price" : this.price
    }

    return this.service.addExaminationType(formData).subscribe(data => {
      if(data['message'] == "true") {
        this.name = '';
        this.price = '';
        this.eventsSubject.next();
        this.isCollapsed = true;
      }
      else {
        this.errorMessage = "Tip pregleda već postoji."
      }
    });
  }
}

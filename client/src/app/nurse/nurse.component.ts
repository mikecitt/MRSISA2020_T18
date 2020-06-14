import { Component, OnInit } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";
import { Subject } from 'rxjs';
import { NurseService, ToastService } from '../service';
@Component({
  selector: 'app-nurse',
  templateUrl: './nurse.component.html',
  styleUrls: ['./nurse.component.css'],
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
export class NurseComponent implements OnInit {
  form = this.formBuilder.group({
    emailAddress: ['', Validators.compose([Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")])],
    password: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
    repeat: ['', Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(64)])],
    firstName: ['', Validators.compose([Validators.required])],
    lastName: ['', Validators.compose([Validators.required])],
    address: ['', Validators.compose([Validators.required])],
    city: ['', Validators.compose([Validators.required])],
    country: ['', Validators.compose([Validators.required])],
    contact: ['', Validators.compose([Validators.required, Validators.pattern("[+]?^[0-9]+"),])],
    jmbg: ['', Validators.compose([Validators.required, Validators.minLength(13), Validators.pattern("^[0-9]+"), Validators.maxLength(13)])],
  });

  eventsSubject: Subject<void> = new Subject<void>();

  errorRePassword = false;
  errorMessage = null;

  public isCollapsed = true;

  constructor(private service:NurseService,
              private formBuilder:FormBuilder,
              private toastService:ToastService) { }

  ngOnInit() { }

  addNurse() {
    this.errorRePassword = false;
    this.errorMessage = null;

    if (this.form.controls['repeat'].value !== this.form.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    let formObj = this.form.getRawValue();
    delete formObj['repeat'];

    return this.service.addNurse(formObj).subscribe(data => {
      if(data['message'] == "true") {
        this.toastService.show('Dodavanje uspešno.', { classname: 'bg-success text-light', delay: 3000 });
        this.form.reset();
        this.isCollapsed = true;
        this.eventsSubject.next();
      }
      else {
        this.errorMessage = "Korisnik vec postoji."
      }
    });

  }
}

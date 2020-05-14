import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { CcadminService } from '../service';

import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface Patient {
	firstName: string;
	lastName: string;
	jmbg: string;
}

@Component({
  selector: 'app-registration-req',
  templateUrl: './registration-req.component.html',
  styleUrls: ['./registration-req.component.css']
})
export class RegistrationReqComponent implements OnInit {

  data: Patient[] = [];
  patients: Observable<Patient[]>;
  jmbg:string = '';
  accept:boolean = null;
  message:string = '';

  constructor(private adminService: CcadminService) { }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients() {
    this.adminService.getUnregistered().subscribe(data => {
      for (let e in data['unregistered'])
        this.data.push({firstName: data['unregistered'][e].firstName, lastName: data['unregistered'][e].lastName, jmbg: data['unregistered'][e].jmbg});
    });
  }

  rowSelected(patient) {
    this.jmbg = patient.jmbg;
  }

  processRegistration() {
    var acceptance = {
      userJmbg: this.jmbg,
      accept: this.accept,
      message: this.message
    }
    this.jmbg = '';
    this.accept = false;
    this.message = '';
    this.adminService.postAcceptance(acceptance).subscribe(data => {
      if(data['status'] !== 'error') {
        window.location.reload();
      }
    });
  }

  deleteRow() {
      for(let i = 0; i < this.data.length; ++i){
          if (this.data[i].jmbg === this.jmbg) {
              this.data.splice(i,1);
          }
      }
  }
}

import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, FormBuilder, Validators } from "@angular/forms";
import { CladminService, ClinicService, Clinic } from '../service';

/*interface Clinic {
	id: number;
	name: string;
}*/

@Component({
  selector: 'app-admincl',
  templateUrl: './admincl.component.html',
  styleUrls: ['./admincl.component.css']
})

export class AdminClComponent implements OnInit {
  form = this.formBuilder.group({
    clinicId: ['', Validators.compose([Validators.required])],
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

  clinics: Clinic[] = [];

  message:string = null;
  alertType:string = null;

  errorRePassword = false;

  constructor(private service:CladminService,
              private clinicService:ClinicService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.clinicService.getClinics().subscribe(data => {
      this.clinics = data as Clinic[];
      //console.log(data);
    })
  }

  addAdmin() {
    this.errorRePassword = false;

    if (this.form.controls['repeat'].value !== this.form.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    let formObj = this.form.getRawValue();
    delete formObj['repeat'];
    let clinicId = formObj['clinicId'];
    delete formObj['clinicId'];

    return this.service.addClAdmin(formObj, clinicId).subscribe(data => {
      if(data['message'] == "true") {
        this.message = "Admin klinike uspesno dodat."
        this.alertType = "success"
      }
      else {
        this.message = "Korisnik vec postoji."
        this.alertType = "danger"
      }
    });
  }
}

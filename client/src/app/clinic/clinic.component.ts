import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { ClinicService } from '../service';

@Component({
  selector: 'app-clinic',
  templateUrl: './clinic.component.html',
  styleUrls: ['./clinic.component.css']
})
export class ClinicComponent implements OnInit {

  name:string = '';
  address:string = '';
  description:string = '';
  message:string = null;
  alertType:string = null;

  constructor(private service:ClinicService) { }

  ngOnInit(): void {
  }

  addClinic() {
    var formData = {
      "name"   : this.name,
      "address"    : this.address,
      "description": this.description
    }

    if(this.name == "" || this.address == "" || this.description == "") {
      this.message = "Sva polja moraju biti popunjena.";
      this.alertType = "warning"
    }
    else {
      return this.service.addClinic(formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Klinika uspešno dodat."
          this.alertType = "success"
        }
        else {
          this.message = "Klinka već postoji."
          this.alertType = "danger"
        }
      });
    }
  }
}

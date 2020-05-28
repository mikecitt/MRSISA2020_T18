import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { CodebookService } from '../service';

@Component({
  selector: 'app-codebook',
  templateUrl: './codebook.component.html',
  styleUrls: ['./codebook.component.css']
})
export class CodebookComponent implements OnInit {

  name:string = '';
  typeCode:string = '';
  message:string = null;
  alertType:string = null;

  constructor(private service:CodebookService) { }

  ngOnInit(): void {
  }

  add() {
    var formData = {
      "name"   : this.name
    }

    if(this.typeCode == 'drugs') {
      return this.service.addDrug(formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Lek uspešno dodat."
          this.alertType = "success"
        }
        else {
          this.message = "Lek već postoji."
          this.alertType = "danger"
        }
      });
    }
    else {
      return this.service.addDiagnosis(formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Dijagnoza uspešno dodat."
          this.alertType = "success"
        }
        else {
          this.message = "Dijagnoza već postoji."
          this.alertType = "danger"
        }
      });
    }
  }
}

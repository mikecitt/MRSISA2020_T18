import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';

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

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
  }

  add() {
    var formData = {
      "name"   : this.name
    }

    if(this.typeCode == 'drugs') {
      return this.http.post<any>('http://localhost:8080/codebook/drug', formData).subscribe(data => {
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
      return this.http.post<any>('http://localhost:8080/codebook/diagnosis', formData).subscribe(data => {
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

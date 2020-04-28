import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';

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

  constructor(private http:HttpClient) { }

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
    }
    else {
      return this.http.post<any>('http://localhost:8080/clinic/add', formData).subscribe(data => {
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

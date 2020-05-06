import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-examination-type',
  templateUrl: './examination-type.component.html',
  styleUrls: ['./examination-type.component.css']
})
export class ExaminationTypeComponent implements OnInit {

  eventsSubject: Subject<void> = new Subject<void>();

  name:string = '';
  price:string = '';
  message:string = null;

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
  }

  addExaminationType() {
    var formData = {
      "name"  : this.name,
      "price" : this.price
    }

    console.log(formData);

    if(this.name == "" || this.price == "") {
      this.message = "Sva polja moraju biti popunjena.";
    }
    else {
      return this.http.post<any>('http://localhost:8080/examination-type/add', formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Tip pregleda uspešno dodat."
          this.eventsSubject.next();
        }
        else {
          this.message = "Tip pregleda već postoji."
        }
      });
    }
  }
}

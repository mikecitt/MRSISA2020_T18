import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  eventsSubject: Subject<void> = new Subject<void>();

  roomNumber:string = '';
  roomType:string = null;
  message:string = null;

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
  }

  addRoom() {
    var formData = {
      "roomNumber": this.roomNumber,
      "type"      : this.roomType
    }

    if(this.roomNumber == "" || this.roomType == null) {
      this.message = "Sva polja moraju biti popunjena.";
    }
    else {
      return this.http.post<any>('http://localhost:8080/room/add', formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Sala uspešno dodata."
        }
        else {
          this.message = "Sala već postoji."
        }
      });
    }
  }
}

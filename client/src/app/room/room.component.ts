import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { Subject } from 'rxjs';
import { RoomService } from '../service';

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

  constructor(private service:RoomService) { }

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
      return this.service.addRoom(formData).subscribe(data => {
        if(data['message'] == "true") {
          this.message = "Sala uspešno dodata.";
          this.eventsSubject.next();
        }
        else {
          this.message = "Sala već postoji.";
        }
      });
    }
  }
}

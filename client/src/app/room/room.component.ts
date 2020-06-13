import { Component, OnInit } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { Subject } from 'rxjs';
import { RoomService } from '../service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css'],
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
export class RoomComponent implements OnInit {

  eventsSubject: Subject<void> = new Subject<void>();

  public isCollapsed = true;

  roomNumber:string = '';
  roomType:string = null;
  errorMessage:string = null;

  constructor(private service:RoomService) { }

  ngOnInit(): void {
  }

  addRoom() {
    this.errorMessage = null;
    var formData = {
      "roomNumber": this.roomNumber,
      "type"      : this.roomType
    }

    return this.service.addRoom(formData).subscribe(data => {
      if(data['message'] == "true") {
        this.roomNumber = '';
        this.roomType = null;
        this.eventsSubject.next();
        this.isCollapsed = true;
      }
      else {
        this.errorMessage = "Sala već postoji.";
      }
    });
  }
}

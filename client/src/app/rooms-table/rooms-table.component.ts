import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { RoomService } from '../service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';

import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface Room {
  roomNumber: string;
  type: string;
}

function search(text:string, pipe: PipeTransform, data: Room[]): Room[] {
  return data.filter(room => {
    const term = text.toLowerCase();
    return room.type.toLowerCase().includes(term) || String(room.roomNumber).includes(term);
  });
}

@Component({
  selector: 'app-rooms-table',
  templateUrl: './rooms-table.component.html',
  styleUrls: ['./rooms-table.component.css'],
  providers: [DecimalPipe]
})
export class RoomsTableComponent implements OnInit {
  @Input() endpoint: string;
  @Input() fieldsList: string[];

  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

  data: Room[] = [];

  rooms: Observable<Room[]>;
  filter = new FormControl('');

  pipe: DecimalPipe;

  constructor(private roomService: RoomService,
    pipe: DecimalPipe) {
      this.pipe = pipe;
    }

  ngOnInit(): void {
    this.eventsSubscription = this.events.subscribe(() => this.initTable());
    this.initTable();
  }

  initTable(): void {
    this.data.length = 0;
    this.roomService.getRooms().subscribe(data => {
      for (let e in data)
        this.data.push({roomNumber: data[e].roomNumber, type: (data[e].type == "OPERATION" ? "Operaciona" : "Sala za preglede")});

        this.rooms = this.filter.valueChanges.pipe(
          startWith(''),
          map(text => search(text, this.pipe, this.data))
        )
    })
  }

  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }

  removeRoom(id) {
    this.roomService.removeRoom(id).subscribe(data => {
      this.initTable();
    });
  }
}

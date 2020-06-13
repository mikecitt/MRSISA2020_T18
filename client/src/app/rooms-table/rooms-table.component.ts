import { Component, OnInit, Input, PipeTransform } from '@angular/core';
import { RoomService } from '../service';
import { DecimalPipe } from '@angular/common';
import { FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RoomEditComponent } from '../room-edit/room-edit.component';
import { RoomCalendarComponent } from '../room-calendar/room-calendar.component';


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
  private eventsSubscription: Subscription;

  @Input() events: Observable<void>;

  data: Room[] = [];
  tableData: Room[] = [];

  filter = new FormControl('');

  pipe: DecimalPipe;

  constructor(private roomService: RoomService,
    pipe: DecimalPipe, private modalService: NgbModal) {
      this.pipe = pipe;

      this.filter.valueChanges.subscribe(val => {
        this.tableData = this.data.filter(entity => {
            const term = val.toLowerCase();
            return entity.type.toLowerCase().includes(term)
                    || String(entity.roomNumber).includes(term);
          })
      })
    }

  ngOnInit(): void {
    this.eventsSubscription = this.events.subscribe(() => this.initTable());
    this.initTable();
  }

  initTable(): void {
    this.data.length = 0;
    this.filter.setValue('');
    this.roomService.getRooms().subscribe(data => {
      for (let e in data)
        this.data.push({roomNumber: data[e].roomNumber, type: (data[e].type == "OPERATION" ? "Operaciona" : "Sala za preglede")});

        this.tableData = this.data;
        //this.rooms = this.filter.valueChanges.pipe(
        //  startWith(''),
        //  map(text => search(text, this.pipe, this.data))
        //)
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

  openCalendar(id): void {
    const modalRef = this.modalService.open(RoomCalendarComponent, {size: 'lg'});
    modalRef.componentInstance.id = id;

    modalRef.result.then((data) => {
    }, (reason) => {
      this.initTable(); // TODO: make better
    });
  }

  openRoomEdit(id): void {
    const modalRef = this.modalService.open(RoomEditComponent);
    modalRef.componentInstance.id = id;

    modalRef.result.then((data) => {
    }, (reason) => {
      this.initTable(); // TODO: make better
    });
  }
}

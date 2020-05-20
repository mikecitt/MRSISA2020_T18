import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DateIntervalComponent } from './date-interval.component';

describe('DateIntervalComponent', () => {
  let component: DateIntervalComponent;
  let fixture: ComponentFixture<DateIntervalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DateIntervalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DateIntervalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

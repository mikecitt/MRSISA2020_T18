import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentStartModalComponent } from './appointment-start-modal.component';

describe('AppointmentStartModalComponent', () => {
  let component: AppointmentStartModalComponent;
  let fixture: ComponentFixture<AppointmentStartModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentStartModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentStartModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

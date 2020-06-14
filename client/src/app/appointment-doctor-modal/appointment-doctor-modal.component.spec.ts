import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentDoctorModalComponent } from './appointment-doctor-modal.component';

describe('AppointmentDoctorModalComponent', () => {
  let component: AppointmentDoctorModalComponent;
  let fixture: ComponentFixture<AppointmentDoctorModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentDoctorModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentDoctorModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

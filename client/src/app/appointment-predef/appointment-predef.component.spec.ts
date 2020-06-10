import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentPredefComponent } from './appointment-predef.component';

describe('AppointmentPredefComponent', () => {
  let component: AppointmentPredefComponent;
  let fixture: ComponentFixture<AppointmentPredefComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentPredefComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentPredefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

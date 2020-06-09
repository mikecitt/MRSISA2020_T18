import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FreetimeDoctorTableComponent } from './freetime-doctor-table.component';

describe('FreetimeDoctorTableComponent', () => {
  let component: FreetimeDoctorTableComponent;
  let fixture: ComponentFixture<FreetimeDoctorTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FreetimeDoctorTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FreetimeDoctorTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

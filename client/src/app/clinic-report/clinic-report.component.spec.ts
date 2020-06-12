import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClinicReportComponent } from './clinic-report.component';

describe('ClinicReportComponent', () => {
  let component: ClinicReportComponent;
  let fixture: ComponentFixture<ClinicReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClinicReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClinicReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

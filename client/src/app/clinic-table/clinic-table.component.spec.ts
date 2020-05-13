import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClinicTableComponent } from './clinic-table.component';

describe('ClinicTableComponent', () => {
  let component: ClinicTableComponent;
  let fixture: ComponentFixture<ClinicTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClinicTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClinicTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

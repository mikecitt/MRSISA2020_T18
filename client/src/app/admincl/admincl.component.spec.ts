import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminClComponent } from './admincl.component';

describe('AdminClComponent', () => {
  let component: AdminClComponent;
  let fixture: ComponentFixture<AdminClComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminClComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminClComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  if('should create', () => {
    expect(component).toBeTruthy();
  })
});

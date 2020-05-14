import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationReqComponent } from './registration-req.component';

describe('RegistrationReqComponent', () => {
  let component: RegistrationReqComponent;
  let fixture: ComponentFixture<RegistrationReqComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationReqComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationReqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

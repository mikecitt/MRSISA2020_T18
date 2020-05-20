import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationReqComponent } from './vacation-req.component';

describe('VacationReqComponent', () => {
  let component: VacationReqComponent;
  let fixture: ComponentFixture<VacationReqComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VacationReqComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VacationReqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

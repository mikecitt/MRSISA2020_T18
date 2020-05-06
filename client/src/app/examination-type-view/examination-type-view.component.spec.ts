import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExaminationTypeViewComponent } from './examination-type-view.component';

describe('ExaminationTypeViewComponent', () => {
  let component: ExaminationTypeViewComponent;
  let fixture: ComponentFixture<ExaminationTypeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExaminationTypeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExaminationTypeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExaminationTypeEditComponent } from './examination-type-edit.component';

describe('ExaminationTypeEditComponent', () => {
  let component: ExaminationTypeEditComponent;
  let fixture: ComponentFixture<ExaminationTypeEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExaminationTypeEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExaminationTypeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

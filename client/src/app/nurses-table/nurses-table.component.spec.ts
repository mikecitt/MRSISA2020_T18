import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursesTableComponent } from './nurses-table.component';

describe('NursesTableComponent', () => {
  let component: NursesTableComponent;
  let fixture: ComponentFixture<NursesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

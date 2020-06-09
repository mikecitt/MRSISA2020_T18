import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchExaminationComponent } from './search-examination.component';

describe('SearchExaminationComponent', () => {
  let component: SearchExaminationComponent;
  let fixture: ComponentFixture<SearchExaminationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchExaminationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchExaminationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

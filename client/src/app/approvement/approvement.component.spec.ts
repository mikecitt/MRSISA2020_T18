import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovementComponent } from './approvement.component';

describe('ApprovementComponent', () => {
  let component: ApprovementComponent;
  let fixture: ComponentFixture<ApprovementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprovementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

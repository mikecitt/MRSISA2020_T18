import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckRecipeComponent } from './check-recipe.component';

describe('CheckRecipeComponent', () => {
  let component: CheckRecipeComponent;
  let fixture: ComponentFixture<CheckRecipeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckRecipeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckRecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

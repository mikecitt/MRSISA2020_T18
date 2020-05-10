import { TestBed } from '@angular/core/testing';

import { MedicalGuard } from './medical.guard';

describe('MedicalGuard', () => {
  let guard: MedicalGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(MedicalGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});

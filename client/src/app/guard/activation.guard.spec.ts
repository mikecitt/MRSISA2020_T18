import { TestBed } from '@angular/core/testing';

import { ActivationGuard } from './activation.guard';

describe('ActivationGuard', () => {
  let guard: ActivationGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ActivationGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});

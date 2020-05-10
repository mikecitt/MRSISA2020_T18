import { TestBed } from '@angular/core/testing';

import { AdminclGuard } from './admincl.guard';

describe('AdminclGuard', () => {
  let guard: AdminclGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AdminclGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { CcadminService } from './ccadmin.service';

describe('CcadminService', () => {
  let service: CcadminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CcadminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

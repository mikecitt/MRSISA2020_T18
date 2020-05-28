import { TestBed } from '@angular/core/testing';

import { CladminService } from './cladmin.service';

describe('CladminService', () => {
  let service: CladminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CladminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

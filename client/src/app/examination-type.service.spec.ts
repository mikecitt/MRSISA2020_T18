import { TestBed } from '@angular/core/testing';

import { ExaminationTypeService } from './examination-type.service';

describe('ExaminationTypeService', () => {
  let service: ExaminationTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExaminationTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

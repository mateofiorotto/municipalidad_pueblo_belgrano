import { TestBed } from '@angular/core/testing';

import { TransparenciesService } from './transparencies.service';

describe('TransparenciesService', () => {
  let service: TransparenciesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransparenciesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

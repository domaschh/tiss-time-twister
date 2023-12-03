import { TestBed } from '@angular/core/testing';

import { CalendarReferenceService } from './calendar.reference.service';

describe('CalendarReferenceService', () => {
  let service: CalendarReferenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CalendarReferenceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

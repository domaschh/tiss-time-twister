import { TestBed } from '@angular/core/testing';

import { CalendarReferenceService } from './calendar.reference.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('CalendarReferenceService', () => {
  let service: CalendarReferenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(CalendarReferenceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

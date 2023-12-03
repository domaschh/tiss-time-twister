import { ComponentFixture, TestBed } from '@angular/core/testing';
import {CalendarImportComponent} from "./calendar-import.component";


describe('CalendarImportComponent', () => {
  let component: CalendarImportComponent;
  let fixture: ComponentFixture<CalendarImportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CalendarImportComponent]
    });
    fixture = TestBed.createComponent(CalendarImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

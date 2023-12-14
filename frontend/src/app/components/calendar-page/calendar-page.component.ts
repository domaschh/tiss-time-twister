import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
  OnInit,
} from '@angular/core';
import {
  startOfDay,
  endOfDay,
  subDays,
  addDays,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
} from 'date-fns';
import { Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
import { EventColor } from 'calendar-utils';
import { Calendar } from 'src/app/dtos/Calendar';
import { Configuration } from 'src/app/dtos/Configuration';
import { MyCalendarEvent } from 'src/app/dtos/Calendar';

@Component({
  selector: 'app-calendar-page',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar-page.component.html',
  styleUrls: ['./calendar-page.component.scss']
})
export class CalendarPageComponent implements OnInit {

  calendars: Calendar[];
  configurations: Configuration[];

  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  view: CalendarView = CalendarView.Month;

  CalendarView = CalendarView;

  viewDate: Date = new Date();

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  actions: CalendarEventAction[] = [
    {
      label: '<i class="bi bi-pencil"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      },
    },
    {
      label: '<i class="bi bi-trash"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter((iEvent) => iEvent !== event);
        this.handleEvent('Deleted', event);
      },
    },
  ];

  refresh = new Subject<void>();

  events: CalendarEvent[] = [];
  activeDayIsOpen: boolean = true;

  constructor(private modal: NgbModal) { }
  ngOnInit(): void {
    this.calendars = this.getCalendars();
    this.configurations = this.getConfigurations();

    if(this.calendars.length != 0){
      this.calendars.forEach(cal => {
        if(cal.events != null) {
          cal.events.forEach(event => {
            this.addEvent(event, cal);
          });
        }
      });
    }
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({
    event,
    newStart,
    newEnd,
  }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    console.log(event);
    this.modalData = { event, action };
    this.modal.open(this.modalContent, { size: 'lg' });
  }

  addEvent(event: MyCalendarEvent, calendar: Calendar): void {
    const e: CalendarEvent = {
      title: event.title,
      start: event.start,
      end: event.end,
      color: {
        primary: calendar.color,
        secondary: '#ededeb'
      },
      draggable: false,
      resizable: {
        beforeStart: false,
        afterEnd: false
      },
      calendar: calendar,
      actions: this.actions
    }

    this.events.push(e);
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  getCalendars(): Calendar[] {
    //TODO: get calendars from calendar service
    //No way to store recurring events curently
    return [
      { name: "Calendar 1", id: 1, color: "#fcd303", isActive: false, events: [
        {
          title: "JF",
          start: new Date(2023, 11, 8, 12, 0, 0, 0),
          end: new Date(2023, 11, 8, 13, 0, 0, 0),
          id: 1
        },
        {
          title: "JF",
          start: new Date(2023, 11, 15, 12, 0, 0, 0),
          end: new Date(2023, 11, 15, 13, 0, 0, 0),
          id: 2
        }
      ] },
      { name: "Calendar 2", id: 2, color: "#166624", isActive: false, events: [
        {
          title: "IR1",
          start: new Date(2023, 11, 6, 15, 0, 0, 0),
          end: new Date(2023, 11, 6, 16, 0, 0, 0),
          id: 1 
        }
      ] },
      { name: "Calendar 3", id: 3, color: "#1f0a8a", isActive: false },
    ]
  }

  getConfigurations(): Configuration[] {
    //TODO: get configurations from configuration service
    return [
      { name: "Konfiguration 1", id: 1, color: "#fcd303", isActive: true },
      { name: "Konfiguration 2", id: 2, color: "#166624", isActive: false },
      { name: "Konfiguration 3", id: 3, color: "#1f0a8a", isActive: false },
    ]
  }

  openModal(modalName: string){
    //open the corresponding modal window
  }

  get allCalEnabled(): boolean {
    return this.calendars.map(c => c.isActive).every(x => x === true);
  }
  set allCalEnabled(value: boolean){
    this.calendars.forEach(c => c.isActive = value);
  }
}

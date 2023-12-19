import { Router } from '@angular/router';
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
import { Observable, Subject } from 'rxjs';
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
import { CalendarReferenceService } from 'src/app/services/calendar.reference.service';
import { ConfigurationService } from 'src/app/services/configuration.service';
import * as ICAL from 'ical.js';


//preset colors since color should not be saved
const colors: Record<number, EventColor> = {
  0: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  1: {
    primary: '#03ad2b',
    secondary: '#32e65c',
  },
  2: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

@Component({
  selector: 'app-calendar-page',
  changeDetection: ChangeDetectionStrategy.Default,
  templateUrl: './calendar-page.component.html',
  styleUrls: ['./calendar-page.component.scss']
})
export class CalendarPageComponent implements OnInit {

  myICAL: ICAL = ICAL;
  calendars: Calendar[] = [];
  configurations: Configuration[] = [];

  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();

  loggedInUsername: string | null = null;

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

  events: MyCalendarEvent[] = [];
  activeDayIsOpen: boolean = true;

  constructor(
    private modal: NgbModal,
    private calenderReferenceServie: CalendarReferenceService,
    private configurationService: ConfigurationService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.loadCalendars();
    this.configurations = this.getConfigurations();
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
    const e: MyCalendarEvent = {
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
      actions: this.actions,
      calendar: calendar
    }

    this.events.push(e);
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  loadCalendars() {
    var importedcals: Calendar[] = [];
    var id: number = 0;

    const obs: Observable<String[]> = this.calenderReferenceServie.getAll();
    obs.subscribe({
      next: cals => {
        cals.forEach(ical => {
          var evs: MyCalendarEvent[] = [];

          var parsedcal = this.myICAL.parse(ical);
          var calAsComponent = new this.myICAL.Component(parsedcal);
          var vevents = <any[]>calAsComponent.getAllSubcomponents("vevent");
          vevents.forEach(event => {
            evs.push({
              start: new Date(event.getFirstPropertyValue("dtstart")),
              end: new Date(event.getFirstPropertyValue("dtend")),
              title: event.getFirstPropertyValue("summary"),
            })
          })
          var newcal: Calendar = {
            isActive: false,
            name: calAsComponent.getFirstPropertyValue("prodid"),
            color: colors[id].primary, //only n preset colors are stored
            events: evs,
            id: id //id needed for frontend
          }
          id++;
          this.calendars.push(newcal);
        })
        if (this.calendars != null && this.calendars.length != 0) {
          this.calendars.forEach(cal => {
            if (cal.events != null) {
              cal.events.forEach(event => {
                this.addEvent(event, cal);
              });
            }
          });
        }
      }
    });
  }

  getConfigurations(): Configuration[] {
    //TODO: get configurations from configuration service
    return this.configurationService.getAll();
  }

  openModal(modalName: string) {
    //open the corresponding modal window
  }

  get allCalEnabled(): boolean {
    if (this.calendars != null)
      return this.calendars.map(c => c.isActive).every(x => x === true);
  }
  set allCalEnabled(value: boolean) {
    if (this.calendars != null)
      this.calendars.forEach(c => c.isActive = value);
  }

  logout(): void {
    window.localStorage.removeItem('authToken');
    this.router.navigate(['/'], {queryParams: {loggedOut: 'true'}});
  }

  toggleDarkMode(): void {
    // Implement your dark mode toggling logic here
  }

  editPassword(): void {
    // Implement the password editing functionality here
  }
}

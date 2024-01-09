import {ActivatedRoute, Router} from '@angular/router';
import {Component, TemplateRef, ViewChild,} from '@angular/core';
import {isSameDay, isSameMonth,} from 'date-fns';
import {Subject} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CalendarEvent, CalendarEventTimesChangedEvent, CalendarView,} from 'angular-calendar';
import {EventColor} from 'calendar-utils';
import {Calendar, EventCalendar, MyCalendarEvent} from 'src/app/dtos/Calendar';
import {CalendarReferenceService} from 'src/app/services/calendar.reference.service';
import {ConfigurationService} from 'src/app/services/configuration.service';
import * as ICAL from 'ical.js';
import {EventService} from "../../services/event.service";
import {ToastrService} from "ngx-toastr";
import {ConfigurationDto} from 'src/app/dtos/configuration-dto';
import {Location} from "@angular/common";

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
  selector: 'app-preview-config',
  templateUrl: './preview-config.component.html',
  styleUrls: ['./preview-config.component.scss']
})

export class PreviewConfigComponent {

  myICAL: ICAL = ICAL;
  calendars: Calendar[];
  calId: number;
  config: ConfigurationDto;

  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  refresh = new Subject<void>();

  events: MyCalendarEvent[] = [];
  activeDayIsOpen: boolean = true;

  constructor(
    private modal: NgbModal,
    private readonly location: Location,
    private calenderReferenceServie: CalendarReferenceService,
    private configurationService: ConfigurationService,
    private eventService: EventService,
    private router: Router,
    private modalService: NgbModal,
    private readonly toastrService: ToastrService,
    private route: ActivatedRoute
  ) {
    const data = router.getCurrentNavigation().extras.state;
    this.calId = data?.calId ?? 0;
    this.config = data?.config ?? null;
   }

  ngOnInit(): void {
    this.getPreview();
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
          location: iEvent.location,
          categories: iEvent.categories
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    let myEvent: MyCalendarEvent = this.events.find(ev => ev.id === event.id);
    if(action === 'Clicked' || action === 'Edited'){
      if (!myEvent) {
        console.error("Error while handling event", event);
      } else if (!myEvent.categories.includes('customEvent')) {
        //TODO: handle edit of calendar event
      } else {
        let calendars: EventCalendar[] = this.calendars.map(cal => {
          return {id: cal.id, name: cal.name}
        });
        this.router.navigate(['event/edit', myEvent.id], {
          state: {calendars: calendars}
        });
      }
    } else if(action == 'Deleted') {
      if (!myEvent) {
        console.error("Error while handling event", event);
      } else if (!myEvent.categories.includes('customEvent')) {
        //TODO: handle edit of calendar event
      } else {
        this.eventService.deleteEvent(myEvent.id).subscribe({
          next: _ => {
            console.log("Deleted successfully");
            this.getPreview();
          },
          error: error => {
            console.error('error deleting event', error);
          }
        })
      }
    } else {
      this.modalData = {event, action};
      this.modal.open(this.modalContent, {size: 'lg'});
    }
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  getPreview() {
    console.log(this.config)
    console.log(this.calId)
    this.calenderReferenceServie.getConfigurationPreview(this.calId, this.config).subscribe({
      next: cal => {
            var evs: MyCalendarEvent[] = [];
            var parsedcal = this.myICAL.parse(cal);
            var calAsComponent = new this.myICAL.Component(parsedcal);
            var vevents = <any[]>calAsComponent.getAllSubcomponents("vevent");
            vevents.forEach(event => {
              let parsed = {
                start: new Date(event.getFirstPropertyValue("dtstart")),
                end: new Date(event.getFirstPropertyValue("dtend")),
                title: event.getFirstPropertyValue("summary"),
                location: event.getFirstPropertyValue("location"),
                categories: event.getFirstPropertyValue("categories"),
              };
              evs.push(parsed)
            })
            this.events = evs;
      }
    });
  }

  goToConfigBuilder() {
    this.router.navigate(['createConfig'], {
      state: { calId: this.calId, config: this.config }
    })
  }

  saveConfiguration() {
    this.configurationService.createConfiguration(this.calId, this.config).subscribe({
      next: (createdConfiguration) => {
        this.toastrService.success("Sucessfully created configuration")
        this.router.navigate(['calendar'])
      },error: () => {
        this.toastrService.error("Coudln't Create Configuration")
      }
    })
  }
}

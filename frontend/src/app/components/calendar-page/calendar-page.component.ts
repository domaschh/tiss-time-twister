import { Router } from '@angular/router';
import * as uuid from 'uuid';
import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
  OnInit,
} from '@angular/core';
import {
  isSameDay,
  isSameMonth,
} from 'date-fns';
import { Observable, Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarDayViewBeforeRenderEvent,
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent, CalendarMonthViewBeforeRenderEvent,
  CalendarView, CalendarWeekViewBeforeRenderEvent,
} from 'angular-calendar';
import { EventColor } from 'calendar-utils';
import {Calendar, EventCalendar} from 'src/app/dtos/Calendar';
import { Configuration } from 'src/app/dtos/Configuration';
import { MyCalendarEvent } from 'src/app/dtos/Calendar';
import { CalendarReferenceService } from 'src/app/services/calendar.reference.service';
import { ConfigurationService } from 'src/app/services/configuration.service';
import * as ICAL from 'ical.js';
import {EventService} from "../../services/event.service";
import {ConfirmationModal} from "../delete-modal/confirmation-modal.component";
import {ToastrService} from "ngx-toastr";
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {colors} from "../../global/constants";
import {ConfigurationDto} from "../../dtos/configuration-dto";
import {ConfigImportComponent} from "../calendar-import/config-import.component";
import {ca} from "date-fns/locale";

//preset colors since color should not be saved


@Component({
  selector: 'app-calendar-page',
  changeDetection: ChangeDetectionStrategy.Default,
  templateUrl: './calendar-page.component.html',
  styleUrls: ['./calendar-page.component.scss']
})
export class CalendarPageComponent implements OnInit {

  myICAL: ICAL = ICAL;
  calendars: Calendar[] = [];
  configurations: ConfigurationDto[] = [];

  private onlyUnique(value, index, array) {
    return array.indexOf(value) === index;
  }
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  view: CalendarView = CalendarView.Week;
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
    private eventService: EventService,
    private router: Router,
    private modalService: NgbModal,
    private readonly toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.loadCalendars();
    this.loadConfigs();
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

  loadConfigs() {
    this.configurationService.getAll().subscribe({
      next: (configs) => {
        console.log(configs)
        this.configurations = configs;
      },
      error: () => {
        this.toastrService.error("Coudln't fetch configurations")
      }
    })
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
      console.log(myEvent);
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
            this.loadCalendars();
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
      location: event.location,
      categories: event.categories,
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
    var id: number = 0;

    this.calenderReferenceServie.getAll().subscribe({
      next: cals => {
        cals.forEach((calendarReferenceDto) => {
          console.log(calendarReferenceDto)
          var evs: MyCalendarEvent[] = [];
          this.calenderReferenceServie.getIcalFileFromToken(calendarReferenceDto.token).subscribe((icalString) => {
            var parsedcal = this.myICAL.parse(icalString);
            var calAsComponent = new this.myICAL.Component(parsedcal);
            var vevents = <any[]>calAsComponent.getAllSubcomponents("vevent");
            vevents.forEach(event => {
              let parsed: MyCalendarEvent = {
                start: new Date(event.getFirstPropertyValue("dtstart")),
                end: new Date(event.getFirstPropertyValue("dtend")),
                title: event.getFirstPropertyValue("summary"),
                location: event.getFirstPropertyValue("location"),
                categories: event.getFirstPropertyValue("categories"),
              };
              evs.push(parsed)
            })
            var newcal: Calendar = {
              isActive: true,
              token: calendarReferenceDto.token,
              link: calendarReferenceDto.link,
              name: calendarReferenceDto.name,
              configs: calendarReferenceDto.configurations,
              color: colors[id].primary, //only n preset colors are stored
              events: evs,
              id: calendarReferenceDto.id //id needed for frontend
            }
            id++;
            this.calendars.push(newcal);


            if (this.calendars.length != 0) {
              this.calendars.forEach(cal => {
                if (cal.events != null) {
                  cal.events.forEach(event => {
                    this.addEvent(event, cal);
                  });
                }
              });
            }
          })
        })
      }
    });
  }

  getConfigurations(): ConfigurationDto[] {
    //TODO: get configurations from configuration service
    return []
    // return this.configurationService.getAll();
  }

  openModal(modalName: string) {
    this.router.navigate([modalName])
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


  editPassword(): void {
    // Implement the password editing functionality here
  }

  createCustomEvent() {
    let calendars: EventCalendar[] = this.calendars.map(cal => {
      return {id: cal.id, name: cal.name}
    });
    this.router.navigate(['event/create'], {
      state: {calendars: calendars}
    });
  }

  openDeleteModal(calendar: Calendar) {
    const modalRef = this.modalService.open(ConfirmationModal);
    modalRef.componentInstance.message = 'Do you really want to delete Calendar: ' + calendar.name;
    modalRef.componentInstance.title = 'Confirm deletion' + calendar.name;
    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      this.calenderReferenceServie.deleteCalendar(calendar.id).subscribe({
        next: () => {
          this.toastrService.success("Deleted Calendar");
          this.calendars = this.calendars.filter(obj => obj.id !== calendar.id);

          callback(true);
        },
        error: () => {
          this.toastrService.error("Couldn't delete");
          callback(false);
        }
      });
    };
  }

  openEditPage(calendar: Calendar) {
    let extras = {state: {editMode: true, id: calendar.id}};
    this.router.navigate(['import'], extras)
  }

  openTokenModal(calendar: Calendar) {
    const modalRef = this.modalService.open(ConfirmationModal);
    modalRef.componentInstance.message = this.calenderReferenceServie.getIcalLinkFromToken(calendar.token);
    modalRef.componentInstance.title = 'Regenerate a token for calendar: ' + calendar.name;
    modalRef.componentInstance.isToken = true;

    const toImport: CalendarReferenceDto = {
      id: calendar.id,
      name: calendar.name,
      link: calendar.link,
      configurations: calendar.configs,
      token: uuid.v4()
    }
    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      this.calenderReferenceServie.upsertCalendar(toImport).subscribe({
        next: (response) => {
          this.toastrService.success("Regenerated Token");
          var index = this.calendars.findIndex(obj => obj.id === response.id);
          this.calendars[index].token = response.token;
          modalRef.componentInstance.message = 'https://localhost:8080/export/' + response.token
        },
        error: () => {
          this.toastrService.error("Couldn't generate token");
        }
      });
    };
  }
  openConfigurationPage(edit: boolean) {
    this.router.navigate(['createConfig'], {
      state: {calendars: this.calendars, edit: false}
    });
    }
  removeConfiguraion(config: ConfigurationDto) {
    const modalRef = this.modalService.open(ConfirmationModal);
    modalRef.componentInstance.title = 'Deletion Confirmation';
    modalRef.componentInstance.message = 'Are you sure you want to delete: ' + config.title;
    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      const calendarReferenceId = this.calendars.filter(c => c.configs.map(config => config.id).includes(config.id))
      calendarReferenceId.forEach(calendar => {
        this.calenderReferenceServie.removeFromCalendar(calendar.id, config.id).subscribe({
          next: () => {
            this.toastrService.success("Successfully deleted Configuration")
            this.configurations = this.configurations.filter(obj => obj.id !== config.id);
            this.calendars.forEach(cal => {
              if (cal.id == calendar.id) {
                cal.configs = cal.configs.filter(c => c.id != config.id)
              }
            })
            callback(true)
          }, error: () => {
            this.toastrService.error("Couldn't delete. Consider removing in the public page")
            callback(false)
          }
        })
      })
    }
  }

  openImportModal() {
    const modalRef = this.modalService.open(ConfigImportComponent);
    modalRef.componentInstance.calendars = this.calendars.map(cal => ({
      id: cal.id,
      name: cal.name,
      link: cal.link
    }))

    modalRef.componentInstance.addedConfiguration.subscribe(addedConfiguration => {
      this.configurations.push(addedConfiguration)
    })
  }

  copyLinkToClipboard(confiId: number) {
    this.toastrService.success("Copied link to clipboard")
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', ("http://localhost:8080/api/v1/calendar/?/" + confiId));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
  }

  downloadConfigFile(conf: ConfigurationDto) {
    delete conf.id
    conf.rules.forEach(rule => {
      delete rule.id
      delete rule.effect.id
      delete rule.match.id
    })

    const blob = new Blob([JSON.stringify(conf, null, 2)], { type: 'application/json' });

    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = conf.title + '.json';

    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
  }

  //with config applied
  downloadCalendar(calendar: Calendar) {
    this.calenderReferenceServie.getIcalFileFromToken(calendar.token).subscribe({
      next: (result) => {
        const blob = new Blob([result], { type: 'text/calendar' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = calendar.name + '.ics'; // Assuming you have a title in your calendar object
        document.body.appendChild(a);
        a.click();

        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
        this.toastrService.success("Successfully downloaded ical")
      }, error: () => {
        this.toastrService.error("Couldn't download calendar with applied configs")
      }
    })
  }

  protected readonly ca = ca;
}

import {Router} from '@angular/router';
import * as uuid from 'uuid';
import {ChangeDetectionStrategy, Component, OnInit, TemplateRef, ViewChild,} from '@angular/core';
import {isSameDay, isSameMonth,} from 'date-fns';
import {Subject} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent, CalendarView,} from 'angular-calendar';
import {Calendar, EventCalendar, MyCalendarEvent} from 'src/app/dtos/Calendar';
import {CalendarReferenceService} from 'src/app/services/calendar.reference.service';
import {ConfigurationService} from 'src/app/services/configuration.service';
import * as ICAL from 'ical.js';
import {EventService} from "../../services/event.service";
import {ConfirmationModal} from "../delete-modal/confirmation-modal.component";
import {ToastrService} from "ngx-toastr";
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {ConfigurationDto} from "../../dtos/configuration-dto";
import {ConfigImportComponent} from "../calendar-import/config-import.component";
import {TagDto} from "../../dtos/tag-dto";
import {TagService} from "../../services/tag.service";

//preset colors since color should not be saved

import {EventColor} from "calendar-utils";

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
  tags: TagDto[] = [];
  selectedTags: TagDto[] = [];
  filterUnfolded: boolean = false;
  @ViewChild('modalContent', {static: true}) modalContent: TemplateRef<any>;

  view: CalendarView = CalendarView.Week;
  CalendarView = CalendarView;
  viewDate: Date = new Date();

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  actions: CalendarEventAction[] = []
  // actions: CalendarEventAction[] = [
  //   {
  //     label: '<i class="bi bi-pencil"></i>',
  //     a11yLabel: 'Edit',
  //     onClick: ({event}: { event: CalendarEvent }): void => {
  //       this.handleEvent('Edited', event);
  //     },
  //   },
  //   {
  //     label: '<i class="bi bi-trash"></i>',
  //     a11yLabel: 'Delete',
  //     onClick: ({event}: { event: CalendarEvent }): void => {
  //       this.events = this.events.filter((iEvent) => iEvent !== event);
  //       this.handleEvent('Deleted', event);
  //     },
  //   },
  // ];

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
    private readonly toastrService: ToastrService,
    private tagService: TagService,
  ) {
  }

  ngOnInit(): void {
    this.loadTags();
    this.loadCalendars();
    this.loadConfigs();

  }

  dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      this.activeDayIsOpen = !((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0);
      this.viewDate = date;
    }
  }

  loadTags() {
    this.tagService.getAll().subscribe({
      next: tags => {
        this.tags = tags;
      },
      error: () => {}
    });
  }

  loadConfigs() {
    this.configurationService.getAll().subscribe({
      next: (configs) => {
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
          categories: iEvent.categories,
          description: iEvent.description
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    let myEvent: MyCalendarEvent = this.events.find(ev => ev.id === event.id);
    if (action === 'Clicked' || action === 'Edited') {
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
    } else if (action == 'Deleted') {
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

  private mapEvent(event: MyCalendarEvent, calendar: Calendar, colorId: number): MyCalendarEvent {
    return {
      id: event.id,
      description: event.description,
      title: event.title
        + '<br>' + event.start.toLocaleTimeString().substring(0, 8) + ' - ' + event.end.toLocaleTimeString().substring(0, 8)
        + '<br>' + event.description
        + '<br>' + event.categories ?? ''
        + '<br>' + event.location ?? '' + '<br>',
      start: event.start,
      end: event.end,
      color: {primary: calendar.color, secondary:calendar.color},
      draggable: false,
      resizable: {
        beforeStart: false,
        afterEnd: false
      },
      actions: this.actions,
      location: event.location ?? '',
      categories: event.categories ?? '',
      calendar: calendar
    };
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  loadCalendars() {
    let id = 0;
    this.calendars = []
    this.calenderReferenceServie.getAll().subscribe({
      next: cals => {
        cals.forEach((calRef) => {
          this.calenderReferenceServie.getIcalFileFromToken(calRef.token, this.selectedTags).subscribe((icalString) => {
            const calAsComponent = new this.myICAL.Component(this.myICAL.parse(icalString));
            const vEvents = <any[]>calAsComponent.getAllSubcomponents("vevent");
            const evs = vEvents.map(event => ({
                id: event.getFirstPropertyValue("uid"),
                start: new Date(event.getFirstPropertyValue("dtstart")),
                end: new Date(event.getFirstPropertyValue("dtend")),
                title: event.getFirstPropertyValue("summary"),
                location: event.getFirstPropertyValue("location"),
                categories: event.getFirstPropertyValue("categories"),
                description: event.getFirstPropertyValue('description')
              })
            )
            this.calendars.push({
              isActive: true,
              token: calRef.token,
              link: calRef.link,
              name: calRef.name,
              configs: calRef.configurations,
              color: calRef.color + '90', //only n preset colors are stored
              events: evs,
              id: calRef.id //id needed for frontend
            });
            this.events = this.calendars.flatMap(cal => cal.events.map(e => this.mapEvent(e, cal, cal.id)))
          })
        })
      }
    });
  }

  openEditConfigModal(config: ConfigurationDto) {
    this.router.navigate(['createConfig'], {
      state: {calId: null, config}
    })
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
    modalRef.componentInstance.title = 'Calendar Deletion Confirmation';
    modalRef.componentInstance.message = 'Do you really want to delete: \'' + calendar.name + '\'';
    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      this.calenderReferenceServie.deleteCalendar(calendar.id).subscribe({
        next: () => {
          this.toastrService.success("Deleted Calendar");
          this.loadCalendars();
          this.refresh.next()
          this.calendars = this.calendars.filter(obj => obj.id !== calendar.id);
          callback(true);
        },
        error: () => {
          this.toastrService.error("Could not delete");
          callback(false);
        }
      });
    };
  }

  openEditPage(calendar: Calendar) {
    this.router.navigate(['import'], {state: {editMode: true, id: calendar.id}})
  }

  openTokenModal(calendar: Calendar) {
    const modalRef = this.modalService.open(ConfirmationModal);
    modalRef.componentInstance.message =
      this.calenderReferenceServie.getIcalLinkFromToken(calendar.token, modalRef.componentInstance.selectedTags);
    modalRef.componentInstance.title = 'Export Calendar: ' + calendar.name;
    modalRef.componentInstance.isToken = true;
    modalRef.componentInstance.tags = this.tags;

    const toImport: CalendarReferenceDto = {
      id: calendar.id,
      name: calendar.name,
      link: calendar.link,
      color: calendar.color,
      configurations: calendar.configs,
      token: uuid.v4()
    }
    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      this.calenderReferenceServie.upsertCalendar(toImport).subscribe({
        next: (response) => {
          this.toastrService.success("Regenerated Token");
          var index = this.calendars.findIndex(obj => obj.id === response.id);
          this.calendars[index].token = response.token;
          modalRef.componentInstance.message =
            this.calenderReferenceServie.getIcalLinkFromToken(response.token, modalRef.componentInstance.selectedTags);
        },
        error: () => {
          this.toastrService.error("Could not generate token");
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
    modalRef.componentInstance.title = 'Configuration Deletion Confirmation';
    modalRef.componentInstance.message = 'Do you really want to delete: \'' + config.title + '\'';
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
            this.toastrService.error("Could not delete. Consider removing in the public page")
            callback(false)
          }
        })
      })
    }
  }

  openConfigurationImportModal() {
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

    const blob = new Blob([JSON.stringify(conf, null, 2)], {type: 'application/json'});

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
    this.calenderReferenceServie.getIcalFileFromToken(calendar.token, this.tags).subscribe({
      next: (result) => {
        const blob = new Blob([result], {type: 'text/calendar'});
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
        this.toastrService.error("Could not download calendar with applied configs")
      }
    })
  }

  tagClicked(tag: TagDto) {
    if (this.selectedTags.includes(tag)) {
      this.selectedTags = this.selectedTags.filter(t => t != tag);
    } else {
      this.selectedTags.push(tag);
    }
  }

  filterButtonClicked() {
    this.filterUnfolded = !this.filterUnfolded;
  }

  applyFilters() {
    this.events = [];
    this.calendars = [];
    this.loadCalendars();
  }

  resetFilter() {
    this.selectedTags = [];
    this.applyFilters();
  }

}

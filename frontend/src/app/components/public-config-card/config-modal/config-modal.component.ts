import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ConfigurationDto} from "../../../dtos/configuration-dto";
import {CalendarReferenceService} from "../../../services/calendar.reference.service";
import {CalendarReferenceDto} from "../../../dtos/calendar-reference-dto";
import {ToastrService} from "ngx-toastr";
import {ConfigurationService} from "../../../services/configuration.service";

export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-config-modal',
  templateUrl: './config-modal.component.html',
  styleUrls: ['./config-modal.component.scss']
})
export class ConfigModalComponent implements OnInit {
  @Input() config: ConfigurationDto;

  @Input() confirmAction: confirmAction;

  calendars: CalendarReferenceDto[] = [];
  selectedCal: number | null;
  alreadyAdded: boolean = false;

  constructor(public activeModal: NgbActiveModal,
              private readonly calendarReferenceService: CalendarReferenceService,
              private readonly toastrService: ToastrService,
              private readonly configurationService: ConfigurationService,
              private readonly cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.loadCalendars()
  }

  loadCalendars() {
    this.calendarReferenceService.getAll().subscribe({
      next: cals => {
        this.calendars = cals
        this.loadConfigs()
      }, error: () => {
        this.toastrService.error("Couldn't fetch Calendars")
      }
    });
  }

  loadConfigs() {
    this.configurationService.getAll().subscribe({
      next: (myConfigs) => {
        if (myConfigs.length >= 1) {
          const foundCalendar = this.calendars.find((c) => c.configurations.map(config => config.id).includes(this.config.id));
          if (foundCalendar) {
            this.selectedCal = foundCalendar.id
          }
          this.alreadyAdded = !(foundCalendar === undefined)
        } else {
          this.alreadyAdded = false;
        }
      },
      error: () => {
        this.toastrService.error("Coudln't fetch configurations")
      }
    })
  }

  doConfirmAction() {
    this.confirmAction((result) => {
      if (result) {
        this.activeModal.close("Closed")
      }
    });
  }


  addToCalendar() {
    if (this.alreadyAdded) {
      this.toastrService.error("Can't add config as it is already added to a calendar")
      return;
    }
    this.calendarReferenceService.addToCalendar(this.selectedCal, this.config.id).subscribe({
      next: (cal)=> {
        this.toastrService.success("Added to calendar")
        this.activeModal.close()
      },error: () => {
    }
    })
  }

  onCalendarChange($event: number) {
    this.selectedCal = $event;
  }

  removeFromCalendar() {
    this.calendarReferenceService.removeFromCalendar(this.selectedCal, this.config.id).subscribe({
      next: (cal)=> {
        this.toastrService.success("Removed from Calendar")
        this.activeModal.close()
      },error: () => {
        this.toastrService.error("Couldn't delete")
      }
    })
  }

  copyLinkToClipboard() {
    this.toastrService.success("Copied link to clipboard")
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', ("http://localhost:8080/api/v1/calendar/?/" + this.config.id));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
  }
}
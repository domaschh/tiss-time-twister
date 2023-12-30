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
  alreadyAdded: boolean;

  constructor(public activeModal: NgbActiveModal,
              private readonly calendarReferenceService: CalendarReferenceService,
              private readonly taostrServcie: ToastrService,
              private readonly configurationService: ConfigurationService,
              private readonly cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.loadCalendars()
    this.loadConfigs()
  }

  loadCalendars() {
    this.calendarReferenceService.getAll().subscribe({
      next: cals => {
        this.calendars = cals
      }, error: () => {
        this.taostrServcie.error("Couldn't fetch Calendars")
      }
    });
  }

  loadConfigs() {
    this.configurationService.getAll().subscribe({
      next: (configs) => {
        if (configs.length >= 1) {
          this.alreadyAdded = !(configs.findIndex((c) => c.id === this.config.id) == -1)
        }
      },
      error: () => {
        this.taostrServcie.error("Coudln't fetch configurations")
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
    this.calendarReferenceService.addToCalendar(this.selectedCal, this.config.id).subscribe({
      next: (cal)=> {
        this.taostrServcie.success("Added to calendar")
      },error: () => {
        this.taostrServcie.error("Couldn't add")
    }
    })
  }

  onCalendarChange($event: number) {
    this.selectedCal = $event;
  }
}

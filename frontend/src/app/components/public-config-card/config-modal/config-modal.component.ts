import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {PublicConfigurationDto} from "../../../dtos/configuration-dto";
import {CalendarReferenceService} from "../../../services/calendar.reference.service";
import {CalendarReferenceDto} from "../../../dtos/calendar-reference-dto";
import {ToastrService} from "ngx-toastr";
import {ConfigurationService} from "../../../services/configuration.service";
import {environment} from "../../../../environments/environment";

export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-config-modal',
  templateUrl: './config-modal.component.html',
  styleUrls: ['./config-modal.component.scss']
})
export class ConfigModalComponent implements OnInit {
  @Input() config: PublicConfigurationDto;

  @Input() confirmAction: confirmAction;

  calendars: CalendarReferenceDto[] = [];
  selectedCal: number | null;
  @Input() alreadyAdded: boolean;

  @Output() removedFromPublicPage = new EventEmitter<number>();

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
          const foundCalendar = this.calendars.find((c) => c.configurations.map(config => config.clonedFromId).includes(this.config.id));
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


  addToCalendar() {
    if (this.alreadyAdded) {
      this.toastrService.error("Can't add config as it is already added to a calendar")
      return;
    }
    this.calendarReferenceService.addToCalendar(this.selectedCal, this.config.id).subscribe({
      next: (cal) => {
        this.activeModal.close()
        window.location.reload();
      }, error: () => {
      }
    })
  }

  onCalendarChange($event: number) {
    this.selectedCal = $event;
  }

  copyLinkToClipboard() {
    this.toastrService.success("Copied link to clipboard")
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', ( environment.backendUrl + "/calendar/?/" + this.config.id));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
  }

  removeFromPublicPage() {
    this.configurationService.removeFromPublicPage(this.config.id).subscribe({
      next: () => {
        this.toastrService.success("Deleted public configuration")
        this.removedFromPublicPage.emit(this.config.id);
        this.activeModal.dismiss()
      }
    });
  }
}

import {Component, Input, OnInit} from '@angular/core';
import {ToastrService} from "ngx-toastr";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DefaultConfigModalComponent} from "./default-config-modal/default-config-modal.component";
import {CalendarReferenceService} from "../../../services/calendar.reference.service";
import {CalendarReferenceDto} from "../../../dtos/calendar-reference-dto";

@Component({
  selector: 'app-public-card-fake',
  templateUrl: './public-card-fake.component.html',
  styleUrls: ['./public-card-fake.component.scss']
})
export class PublicCardFakeComponent implements OnInit{
  @Input() desc: string;
  @Input() isAdded: boolean;
  @Input() title: string;
  @Input() bodyToShow: number;
  @Input() defaultConfigNumber: number;

  calendars: CalendarReferenceDto[] = []
  selectedCal: CalendarReferenceDto;

  constructor(private readonly toastrService: ToastrService, private readonly modalService: NgbModal, private readonly calendarService: CalendarReferenceService) {
  }

  ngOnInit() {
    this.loadCalendarReferences()
  }

  openConfigModal() {
    const modalRef = this.modalService.open(DefaultConfigModalComponent);
    modalRef.componentInstance.title = this.title
    modalRef.componentInstance.alreadyAdded = this.isAdded
    modalRef.componentInstance.description = this.desc
    modalRef.componentInstance.bodyToShow = this.bodyToShow
    modalRef.componentInstance.selectedCalendar = this.selectedCal?.id ?? 0
    modalRef.componentInstance.calendars = this.calendars
    modalRef.componentInstance.defaultConfigNumber = this.defaultConfigNumber
    modalRef.componentInstance.changed.subscribe(isAdded => {
      this.isAdded = isAdded
    })
    modalRef.componentInstance.changedCalId.subscribe(calId => {
      this.selectedCal = this.calendars.find(cal => cal.id == calId)
    })
  }

  loadCalendarReferences() {
    this.calendarService.getAll().subscribe({
      next: (calendars) => {
        this.calendars = calendars
        calendars.forEach(cal => {
          if ((cal.enabledDefaultConfigurations & this.defaultConfigNumber) > 0) {
            this.isAdded = true;
            this.selectedCal = cal;
          }
        })
      }, error: () => {
        this.toastrService.error("Failed to load")
      }
    })
  }
}

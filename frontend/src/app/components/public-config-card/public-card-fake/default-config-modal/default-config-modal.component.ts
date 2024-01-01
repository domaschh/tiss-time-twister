import {AfterViewInit, Component, EventEmitter, Input, Output, TemplateRef, ViewChild} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CalendarReferenceService} from "../../../../services/calendar.reference.service";
import {CalendarReferenceDto} from "../../../../dtos/calendar-reference-dto";
import {ToastrService} from "ngx-toastr";
import {th} from "date-fns/locale";
export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-default-config-modal',
  templateUrl: './default-config-modal.component.html',
  styleUrls: ['./default-config-modal.component.scss']
})
export class DefaultConfigModalComponent implements AfterViewInit {
  title: string;
  alreadyAdded: boolean = false
  description: string;
  calendars: CalendarReferenceDto[] = []

  @Input() bodyToShow: number;
  @Input() defaultConfigNumber: number;
  @Output() changed: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() changedCalId: EventEmitter<number> = new EventEmitter<number>();

  @ViewChild('customComponent1') customComponent1Template: TemplateRef<any>;
  @ViewChild('customComponent2') customComponent2Template: TemplateRef<any>;
  @ViewChild('customComponent3') customComponent3Template: TemplateRef<any>;

  componentToShow: TemplateRef<any>;
  selectedCalendar: number;

  constructor(private readonly modal: NgbModal, private readonly calendarReferenceService: CalendarReferenceService, private readonly toastrService: ToastrService) {
  }

  ngAfterViewInit() {
    this.showComponent();
  }


  closeAll() {
    this.modal.dismissAll()
  }

  showComponent() {
    if (this.bodyToShow === 1) {
      this.componentToShow = this.customComponent1Template;
    } else if (this.bodyToShow === 2) {
      this.componentToShow = this.customComponent2Template;
    } else if (this.bodyToShow === 3) {
      this.componentToShow = this.customComponent3Template;
    }
  }

  removeFromCalendar() {
    this.calendarReferenceService.removeFromCalendar(this.selectedCalendar, -this.defaultConfigNumber).subscribe({
      next: (added) => {
        this.toastrService.success("Removed Default configuration")
        this.alreadyAdded = false;
        this.changed.emit(this.alreadyAdded)
        this.changedCalId.emit(null)
        this.modal.dismissAll()
      }, error: () => {
        this.toastrService.error("Failed to add to calendar")
      }
    })
  }

  addToCalendar() {
    this.calendarReferenceService.addToCalendar(this.selectedCalendar, -this.defaultConfigNumber).subscribe({
      next: (added) => {
        this.toastrService.success("Added Default configuration")
        this.alreadyAdded = true;
        this.changed.emit(this.alreadyAdded)
        this.changedCalId.emit(this.selectedCalendar)
        this.modal.dismissAll()
      }, error: () => {
        this.toastrService.error("Failed to add to calendar")
      }
    })
  }
}

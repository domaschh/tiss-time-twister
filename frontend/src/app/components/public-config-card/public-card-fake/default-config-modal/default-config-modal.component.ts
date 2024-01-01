import {AfterViewInit, Component, Input, TemplateRef, ViewChild} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CalendarReferenceService} from "../../../../services/calendar.reference.service";
import {CalendarReferenceDto} from "../../../../dtos/calendar-reference-dto";
import {ToastrService} from "ngx-toastr";

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

  @ViewChild('customComponent1') customComponent1Template: TemplateRef<any>;
  @ViewChild('customComponent2') customComponent2Template: TemplateRef<any>;
  @ViewChild('customComponent3') customComponent3Template: TemplateRef<any>;

  componentToShow: TemplateRef<any>;
  selectedCalendar: CalendarReferenceDto;

  constructor(private readonly modal: NgbModal, private readonly calendarReferenceService: CalendarReferenceService, private readonly toastrService: ToastrService) {
  }

  ngAfterViewInit() {
    this.loadCalendars()
    this.showComponent();
  }


  closeAll() {
    this.modal.dismissAll()
  }

  loadCalendars() {
    this.calendarReferenceService.getAll().subscribe({
      next: (cals) => {
        this.calendars = cals
        console.log(this.calendars)
      }, error: () => {
        this.toastrService.error("Couldn't fetch Calendars")
      }
    })
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

  }

  addToCalendar() {

  }
}

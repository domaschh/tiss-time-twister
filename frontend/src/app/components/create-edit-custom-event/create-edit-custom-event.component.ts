import {Component, OnInit} from '@angular/core';
import {CustomEventDto} from "../../dtos/custom-event-dto";
import {EventService} from "../../services/event.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {EventCalendar} from "../../dtos/Calendar";
import {ToastrService} from "ngx-toastr";
import {TagDto} from "../../dtos/tag-dto";
import {ConfirmationModal} from "../delete-modal/confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

export enum CreateEditMode{
  create,
  edit,
}

export interface dialogData {
  event: CustomEventDto;
  mode: CreateEditMode;
}

@Component({
  selector: 'app-custom-event-modal',
  templateUrl: './create-edit-custom-event.component.html',
  styleUrls: ['./create-edit-custom-event.component.scss']
})
export class CreateEditCustomEventComponent implements OnInit{

  mode: CreateEditMode = CreateEditMode.create;
  event: CustomEventDto = {
    title: '',
    startTime: new Date(),
    endTime: new Date(),
    location: '',
    calendarId: -1
  }

  private startSet: boolean = false;
  private endSet: boolean = false;

  calendars: EventCalendar[];

  get startTime(): Date | null {
    return this.startSet
      ? this.event.startTime
      : null;
  }

  set startTime(value: Date) {
    this.startSet = true;
    this.event.startTime = value;
  }

  get endTime(): Date | null {
    return this.endSet
      ? this.event.endTime
      : null;
  }

  set endTime(value: Date) {
    this.endSet = true;
    this.event.endTime = value;
  }

  constructor(
    private eventService: EventService,
    private router: Router,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private readonly toastrService: ToastrService,
  ) {
    this.calendars = this.router.getCurrentNavigation().extras.state?.calendars;
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.mode = data.mode;
    });

    if (this.mode === CreateEditMode.edit){
      let id = '';
      this.route.params.subscribe(p => id = p.id);

      if (id.includes('customEvent')) {
        id = id.split('_')[1];
      }

      this.eventService.getEventById(id).subscribe({
        next: data => {
          this.event = data;
          this.startTime = data.startTime;
          this.endTime = data.endTime;
        },
        error: error => {
          console.error('error fetching event', error);
          this.toastrService.error('Error fetching event');
          this.router.navigate(['calendar']);
        }
      })
    } else {
      if (!this.calendars) {
        console.log("Cannot create event without calendar!");
        this.router.navigate(['calendar']);
      }
    }
  }

  public get heading(): string {
    switch (this.mode) {
      case CreateEditMode.create:
        return 'Create Event';
      case CreateEditMode.edit:
        return 'Edit Event';
      default:
        return '?';
    }
  }

  onSubmit(form: NgForm) {
    console.log(this.event);
    if(form.valid){
      let observable: Observable<CustomEventDto>;
      switch (this.mode){
        case CreateEditMode.create:
          observable = this.eventService.createEvent(this.event);
          break;
        case CreateEditMode.edit:
          observable = this.eventService.updateEvent(this.event);
          break;
        default:
          console.error('Unknown Mode', this.mode);
          return;
      }

      observable.subscribe({
        next: data => {
          console.log(`success ${data}`);
          let action: string;
          switch (this.mode){
            case CreateEditMode.create:
              action = 'created';
              break;
            case CreateEditMode.edit:
              action = 'edited';
              break;
          }
          this.toastrService.success("Successfully " + action + " event!");
          this.router.navigate(['calendar']);
        },
        error: error => {
          let action: string;
          switch (this.mode){
            case CreateEditMode.create:
              action = 'creating';
              break;
            case CreateEditMode.edit:
              action = 'editing';
              break;
          }
          console.error('Error ' + action + ' event', error);
          this.toastrService.error('Error ' + action + ' event');
        }
      })
    }
  }

  onCancelClick() {
    this.router.navigate(['calendar']);
  }

  openDeleteModal() {
    const modalRef = this.modalService.open(ConfirmationModal);
    modalRef.componentInstance.title = 'Deletion Confirmation';
    modalRef.componentInstance.message = 'Do you really want to delete this event?';
    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      this.eventService.deleteEvent(this.event.id).subscribe({
        next: () => {
          this.toastrService.success("Deleted event");
          this.router.navigate(['calendar']);
          callback(true);
        },
        error: () => {
          this.toastrService.error("Could not delete tag");
          callback(false);
        }
      });
    };
  }

  protected readonly CreateEditMode = CreateEditMode;
}

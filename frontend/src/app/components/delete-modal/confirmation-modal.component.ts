import {Component, Input, Output} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ToastrService} from "ngx-toastr";
import {TagDto} from "../../dtos/tag-dto";

export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-delete-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})
export class ConfirmationModal {
  @Input() message: string = '';
  @Input() confirmAction: confirmAction;
  @Input() tags: TagDto[];

  @Output() selectedTags: TagDto[] = [];

  filterUnfolded: boolean = false;


  isToken: boolean = false;

  @Input() title: string = 'Confirmation Title';

  constructor(public activeModal: NgbActiveModal, private readonly toastrService: ToastrService) {}

  doConfirmAction() {
    this.confirmAction((result) => {
      if (result) {
        this.activeModal.close("Closed")
      }
    });
  }

  copyMessage() {
    this.toastrService.success("Copied link to clipboard")
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', this.message);
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
  }

  tagClicked(tag: TagDto) {
    if (this.selectedTags.includes(tag)) {
      this.selectedTags = this.selectedTags.filter(t => t != tag);
    } else {
      this.selectedTags.push(tag);
    }
    this.doConfirmAction();
  }

  filterButtonClicked() {
    this.filterUnfolded = !this.filterUnfolded;
  }
}

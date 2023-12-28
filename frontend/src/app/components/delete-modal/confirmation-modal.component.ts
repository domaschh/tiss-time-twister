import {Component, Input} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-delete-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})
export class ConfirmationModal {
  @Input() message: string = 'Do your really wanna delete?';
  @Input() confirmAction: confirmAction;

  @Input() title: string = 'Confirmation Title';

  constructor(public activeModal: NgbActiveModal) {}

  doConfirmAction() {
    this.confirmAction((result) => {
      if (result) {
        this.activeModal.close("Closed")
      }
    });
  }
}

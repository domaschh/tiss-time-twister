import {Component, Input} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-config-modal',
  templateUrl: './config-modal.component.html',
  styleUrls: ['./config-modal.component.scss']
})
export class ConfigModalComponent {
  @Input() message: string = 'Do your really wanna delete?';
  @Input() confirmAction: confirmAction;

  @Input() title: string = 'Confirmation Title';
  addedAlready: boolean;

  constructor(public activeModal: NgbActiveModal) {}

  doConfirmAction() {
    this.confirmAction((result) => {
      if (result) {
        this.activeModal.close("Closed")
      }
    });
  }
}

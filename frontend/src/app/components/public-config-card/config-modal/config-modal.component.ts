import {Component, Input} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ConfigurationDto} from "../../../dtos/configuration-dto";

export type confirmAction = (callback: (result: boolean) => void) => boolean;

@Component({
  selector: 'app-config-modal',
  templateUrl: './config-modal.component.html',
  styleUrls: ['./config-modal.component.scss']
})
export class ConfigModalComponent {
  @Input() config: ConfigurationDto;

  @Input() confirmAction: confirmAction;

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

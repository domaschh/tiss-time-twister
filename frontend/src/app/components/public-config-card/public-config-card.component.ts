import {Component, Input} from '@angular/core';
import {ConfigModalComponent} from "./config-modal/config-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-public-config-card',
  templateUrl: './public-config-card.component.html',
  styleUrls: ['./public-config-card.component.scss']
})
export class PublicConfigCardComponent {

  @Input()
  configTitle: string;
  @Input()
  configDescription: string
  addedAlready: boolean;

  constructor(
    private readonly modalService: NgbModal,
    private readonly toastrService: ToastrService
  ) {
  }
  openConfigModal() {
      const modalRef = this.modalService.open(ConfigModalComponent);
      modalRef.componentInstance.message = 'Halli Hallo';
      modalRef.componentInstance.title = 'Geile Configuration';

      modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      };
  }
}

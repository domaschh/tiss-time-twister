import {Component, Input, OnInit} from '@angular/core';
import {ConfigModalComponent} from "./config-modal/config-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ToastrService} from "ngx-toastr";
import {ConfigurationService} from "../../services/configuration.service";
import {ConfigurationDto, PublicConfigurationDto} from "../../dtos/configuration-dto";

@Component({
  selector: 'app-public-config-card',
  templateUrl: './public-config-card.component.html',
  styleUrls: ['./public-config-card.component.scss']
})
export class PublicConfigCardComponent{
  @Input() config: PublicConfigurationDto;
  alreadyAdded: boolean;

  constructor(
    private readonly modalService: NgbModal,
    private readonly toastrService: ToastrService,
    private readonly configurationService: ConfigurationService
  ) {
  }

  openConfigModal() {
      const modalRef = this.modalService.open(ConfigModalComponent);
      console.log(this.config.alreadyCloned)
      modalRef.componentInstance.config = this.config;
      modalRef.componentInstance.alreadyAdded = this.config.alreadyCloned;

      modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
      };
  }
}

import {Component, ComponentFactoryResolver, Input} from '@angular/core';
import {ConfigurationService} from "../../../services/configuration.service";
import {ToastrService} from "ngx-toastr";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DefaultConfigModalComponent} from "./default-config-modal/default-config-modal.component";
import {LvaShorthandsComponent} from "./lva-shorthands/lva-shorthands.component";

@Component({
  selector: 'app-public-card-fake',
  templateUrl: './public-card-fake.component.html',
  styleUrls: ['./public-card-fake.component.scss']
})
export class PublicCardFakeComponent {
  @Input() desc: string;
  @Input() isAdded: string;
  @Input() title: string;
  @Input() bodyToShow: number;

  constructor(private readonly configurationService: ConfigurationService, private readonly toastrService: ToastrService, private readonly modalService: NgbModal) {
  }

  openConfigModal() {
    const modalRef = this.modalService.open(DefaultConfigModalComponent);
    modalRef.componentInstance.title = this.title
    modalRef.componentInstance.alreadyAdded = this.isAdded
    modalRef.componentInstance.description = this.desc
    modalRef.componentInstance.bodyToShow = this.bodyToShow

    modalRef.componentInstance.confirmAction = (callback: (result: boolean) => void) => {
    };
  }
}

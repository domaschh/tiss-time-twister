import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-logout-success-modal',
  templateUrl: './logout-success-modal.component.html',
  styleUrls: ['./logout-success-modal.component.scss']
})
export class LogoutSuccessModalComponent {
    @Input() message: string = 'You have been successfully logged out.';

    constructor(public activeModal: NgbActiveModal) {}
}

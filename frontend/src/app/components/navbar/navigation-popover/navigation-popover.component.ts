import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-navigation-popover',
  templateUrl: './navigation-popover.component.html',
  styleUrls: ['./navigation-popover.component.scss'],
})
export class NavigationPopoverComponent {
  constructor(
    private readonly router: Router,private modal: NgbModal
  ) {
  }

  get isPublicPage() {
    return this.router.url.includes('calendar')
  }

  routeToPage(s: string) {
    this.router.navigate([s])
    this.modal.dismissAll()
  }
}

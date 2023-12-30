import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NavigationPopoverComponent} from "./navigation-popover/navigation-popover.component";
import {NavigationPopoverRightComponent} from "./navigation-popover-right/navigation-popover-right.component";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  constructor(private readonly router: Router,
              private modal: NgbModal,
  ) {
  }

  showNavbar() {
    return localStorage.getItem("authToken")
  }

  toggleDarkMode(): void {
    document.body.classList.toggle('dark');
  }

  isDarkMode(): boolean {
    return document.body.classList.contains('dark');
  }

  showNavModal() {
    this.modal.open(NavigationPopoverComponent, {
      centered: false, fullscreen: "xl", windowClass: 'custom-full-height-modal-left'
    })
  }

  showNavRightModal() {
    this.modal.open(NavigationPopoverRightComponent, {
      centered: false, fullscreen: "xl", windowClass: 'custom-full-height-modal-right'
    })
  }

  navigateMyCalendars() {
    this.router.navigate(['calendar'])
  }
}

import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NavigationPopoverComponent} from "./navigation-popover/navigation-popover.component";
import {NavigationPopoverRightComponent} from "./navigation-popover-right/navigation-popover-right.component";
import {is} from "date-fns/locale";

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
    this.loadDarkMode();
    const authToken = localStorage.getItem("authToken");
    return (authToken != undefined || authToken != null) && this.router.url != '/'
  }

  private loadDarkMode() {
    if (localStorage.getItem('dark') === 'true') {
      document.body.classList.add('dark');
    } else {
      document.body.classList.remove('dark');
    }
  }

  toggleDarkMode(): void {
    if (localStorage.getItem('dark') === null) {
      localStorage.setItem('dark', 'true');
      document.body.classList.add('dark');
    } else {
      localStorage.removeItem('dark');
      document.body.classList.remove('dark');
    }
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

import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ToastrService} from "ngx-toastr";
import {ResetPasswordMode} from "../../reset-password/reset-password.component";
import {data} from "autoprefixer";

@Component({
  selector: 'app-navigation-popover-right',
  templateUrl: './navigation-popover-right.component.html',
  styleUrls: ['./navigation-popover-right.component.scss']
})
export class NavigationPopoverRightComponent {
  constructor(
    private readonly router: Router,private modal: NgbModal,
    private readonly toastrService: ToastrService
  ) {
  }

  logout(): void {
    window.localStorage.removeItem('authToken');
    this.toastrService.info("signed out")
    this.router.navigate(['/']);
    this.modal.dismissAll()
  }

  changePasswort() {
    this.router.navigate(['resetPassword'])
    this.modal.dismissAll()
  }

  navigateHelp() {
    this.router.navigate(['help'])
    this.modal.dismissAll()
  }
}

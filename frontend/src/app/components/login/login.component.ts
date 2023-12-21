import {Component, OnInit, ElementRef, Renderer2  } from '@angular/core';
import {UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {AuthRequest} from '../../dtos/auth-request';

import {
  Validation,
  Input,
  Ripple,
  initTE,
} from "tw-elements";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { th } from 'date-fns/locale';
import { LogoutSuccessModalComponent } from '../logout-success-modal/logout-success-modal.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: UntypedFormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  isMobileView = window.innerWidth < 480;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private authService: AuthService,
    private router: Router,
    private renderer: Renderer2,
    private el: ElementRef,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    ) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });

    // Listen for window resize events
    window.addEventListener('resize', () => {
      this.isMobileView = window.innerWidth > 480;

      // Update button width based on the condition
      if (this.isMobileView) {
        this.renderer.addClass(this.el.nativeElement.querySelector('.login-button'), 'w-72');
      } else {
        this.renderer.removeClass(this.el.nativeElement.querySelector('.login-button'), 'w-72');
      }
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.submitted = true;
    if (this.loginForm.valid) {
      const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.username.value, this.loginForm.controls.password.value);
      this.authenticateUser(authRequest);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   *
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest) {
    console.log('Try to authenticate user: ' + authRequest.email);
    this.authService.loginUser(authRequest).subscribe({
      next: () => {
        console.log('Successfully logged in user: ' + authRequest.email);
        this.router.navigate(['/calendar']);
      },
      error: error => {
        console.log('Could not log in due to:');
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      }
    });
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  ngOnInit() {
    initTE({Validation, Input, Ripple });

    this.activatedRoute.queryParams.subscribe(params => {
      if(params['loggedOut']){
        this.showLogoutSuccessModal();
      }
    });
  }

  private showLogoutSuccessModal(){
    const modalRef = this.modalService.open(LogoutSuccessModalComponent);
    modalRef.componentInstance.message = 'Du wurdest erfolgreich ausgelogged.';
  }

}

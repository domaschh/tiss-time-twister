import { Component, OnInit, ElementRef, Renderer2 } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AuthRequest } from '../../dtos/auth-request';

import {
  Validation,
  Input,
  Ripple,
  initTE,
} from "tw-elements";

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
  errors = {
    auth: '',
    email: '',
    password: ''
  };
  constructor(
    private formBuilder: UntypedFormBuilder,
    private authService: AuthService,
    private router: Router,
    private renderer: Renderer2,
    private el: ElementRef,
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });

    // Listen for window resize events
    window.addEventListener('resize', () => {
      this.isMobileView = window.innerWidth > 480;

      // Update button width based on the condition
      const loginButton = this.el.nativeElement.querySelector('.login-button');
      if (loginButton) {
        if (this.isMobileView) {
          this.renderer.addClass(loginButton, 'w-72');
        } else {
          this.renderer.removeClass(loginButton, 'w-72');
        }
      }
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.submitted = true;
    if (this.loginForm.valid) {
      const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.email.value, this.loginForm.controls.password.value);
      this.authenticateUser(authRequest);
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   *
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest) {
    this.authService.loginUser(authRequest).subscribe({
      next: (authResponse) => {
        localStorage.setItem('authToken', authResponse);
        this.router.navigate(['/calendar']);
      },
      error: error => {
        this.error = true;
        if (typeof error.error === 'object') {
          this.errors.auth = error.error.error || 'authentication failed';
        } else {
          this.errors.auth = error.error || 'authentication failed';
        }
      }
    });
  }
  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
    this.errors.auth = '';
  }

  ngOnInit() {
    const authToken = localStorage.getItem("authToken");
    if ((authToken != undefined || authToken != null) && this.router.url == '/') {
      this.router.navigate(['/calendar'])
    }
    initTE({ Validation, Input, Ripple });
  }
}

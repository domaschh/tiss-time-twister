import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';

import {
  Validation,
  Input,
  Ripple,
  initTE,
} from "tw-elements";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthRequest } from 'src/app/dtos/auth-request';
import { ActivatedRoute, Router } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {


  registrationForm: UntypedFormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  isMobileView = window.innerWidth < 480;
  //Flag for existing email
  emailAlreadyExists = false;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private authService: AuthService,
    private router: Router,
    private renderer: Renderer2,
    private el: ElementRef,
    private toastrService: ToastrService
  ) {
    this.registrationForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\W]*$')]],
      passwordConfirmation: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\W]*$')]]
    });

    // Listen for window resize events
    window.addEventListener('resize', () => {
      this.isMobileView = window.innerWidth > 480;

      // Update button width based on the condition
      if (this.isMobileView) {
        this.renderer.addClass(this.el.nativeElement.querySelector('.registration-button'), 'w-72');
      } else {
        this.renderer.removeClass(this.el.nativeElement.querySelector('.registration-button'), 'w-72');
      }
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  registerUser() {
    this.submitted = true;
    if (this.registrationForm.valid && this.registrationForm.controls.password.value == this.registrationForm.controls.password.value) {
      const authRequest: AuthRequest = new AuthRequest(this.registrationForm.controls.email.value, this.registrationForm.controls.password.value);
      this.authService.registerUser(authRequest).subscribe({
        next: () => {
          console.log('Successfully registered user.');
          this.toastrService.success("Registered")
          this.router.navigate(['/calendar']);
        },
        error: (error) => {
          this.errorHandling(error);
        }
      });
    } else {
      console.log('Invalid input');
    }
  }

  private errorHandling(error: any) {
    console.log('Registrierungsfehler:', error);
    this.error = true;
    this.errorMessage = error.error ? error.error : 'Anmeldung fehlgeschlagen';
    this.emailAlreadyExists = this.errorMessage.includes('E-Mail ist bereits registriert!');
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  ngOnInit() {
    initTE({ Validation, Input, Ripple });
  }



}

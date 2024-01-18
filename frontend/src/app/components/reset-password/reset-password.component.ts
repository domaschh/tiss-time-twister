import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

export enum ResetPasswordMode {
  Forgot,
  Reset,
}

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  mode: ResetPasswordMode;
  ResetPasswordMode = ResetPasswordMode;
  emailForm: FormGroup;
  resetForm: FormGroup;
  submitted = false;
  successMessage = '';
  token: string;
  error = false;
  errorMessage = '';
  isMobileView = window.innerWidth < 480;
  errors = {
    auth: '',
    email: '',
    password: ''
  };

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.emailForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });

    this.resetForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordConfirmation: ['', Validators.required]
    }, { validator: this.checkPasswords });
  }


  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      this.token = params['token'];
      this.mode = this.token ? ResetPasswordMode.Reset : ResetPasswordMode.Forgot;
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.mode === ResetPasswordMode.Forgot) {
      if (this.emailForm.valid) {
        this.authService.requestPasswordReset(this.emailForm.controls.email.value).subscribe({
          next: () => {
            this.successMessage = 'Password reset email sent. Please check your email for the password reset link.'
            setTimeout(() => {
              this.router.navigate(['/']);
            }, 3000);
          },
          error: error => {
            console.error('Reset request failed: ', error);
            this.error = true;
            this.errorMessage = 'Failed to send password reset email. Please try again.!';
          }
        });
      }
    } else if (this.mode === ResetPasswordMode.Reset) {
      if (this.resetForm.valid) {
        this.authService.resetPassword(this.token, this.resetForm.controls.password.value, this.resetForm.controls.passwordConfirmation.value).subscribe({
          next: () => {
            this.successMessage = 'Your password has been reset successfully.'
            setTimeout(() => {
              this.router.navigate(['/']);
            }, 3000);
          },
          error: error => {
            console.error('Password reset failed: ', error);
            this.error = true;
            this.errorMessage = 'Password reset failed. Please try again.';
          }
        });
      }
    }
  }

  private checkPasswords(group: FormGroup): ValidationErrors | null {
    if (!group.controls) {
      return null;
    }
    const pass = group.get('password')?.value;
    const confirmPass = group.get('passwordConfirmation')?.value;
    return pass === confirmPass ? null : { notSame: true };
  }

  /**
 * Error flag will be deactivated, which clears the error message
 */
  vanishError() {
    this.error = false;
    this.errors.auth = '';
  }

}

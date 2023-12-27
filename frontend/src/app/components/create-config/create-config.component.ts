import { Component } from '@angular/core';
import {FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-create-config',
  templateUrl: './create-config.component.html',
  styleUrls: ['./create-config.component.scss']
})
export class CreateConfigComponent {

  importForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    link: ['', [Validators.required]],
    public: [false, [Validators.required]],
  });
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';


  constructor(private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {
  }


  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (typeof error.error === 'object') {
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error;
    }
  }

  vanishError() {
    this.error = false;
  }

  createConfiguration() {

  }
}

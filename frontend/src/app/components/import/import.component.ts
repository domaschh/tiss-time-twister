import {Component} from '@angular/core';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {Router} from "@angular/router";
import {FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportComponent {
  importForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    link: ['', [Validators.required]]
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

  importCalendar() {
    const toImport: CalendarReferenceDto = {
      id: null,
      name: this.importForm.controls.name.value,
      link: this.importForm.controls.link.value,
      token: null
    }

    this.calendarReferenceService.importCalendar(toImport).subscribe({
      next: () => {
        console.log('Successfully imported calendar: ' + toImport.name);
        this.router.navigate(['calendar']);
        this.toastrService.success("Created Calendar")
      },
      error: err => {
        console.log('Could not import calendar due to:');
        console.log(err);
        this.error = true;
        if (typeof err.error === 'object') {
          this.errorMessage = err.error.error;
        } else {
          this.errorMessage = err.error;
        }
      }
    })
  }
}

import {Component} from '@angular/core';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {Router} from "@angular/router";
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportComponent {
  importForm: UntypedFormGroup;
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';


  constructor(private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router) {
    this.importForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      link: ['', [Validators.required]]
    });
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

    const toImport:CalendarReferenceDto = {
      id: null,
      name: this.importForm.controls.name.value,
      link: this.importForm.controls.link.value
    }

    this.calendarReferenceService.importCalendar(toImport).subscribe({
      next: ()=> {
        console.log('Successfully imported calendar: ' + toImport.name);
        this.router.navigate(['']);
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

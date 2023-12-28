import {Component, OnInit} from '@angular/core';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportComponent implements OnInit {
  importForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    link: ['', [Validators.required]]
  });
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  editMode = false;
  optionalEditId: number | null;
  optionalToken: string | null;

  constructor(private formBuilder: UntypedFormBuilder,
              private calendarReferenceService: CalendarReferenceService,
              private router: Router,
              private readonly toastrService: ToastrService) {
    const data = router.getCurrentNavigation().extras.state;
    this.editMode = data?.editMode ?? false;
    this.optionalEditId = data?.id ?? null;
  }

  ngOnInit(): void {
    if (this.editMode) {
      this.calendarReferenceService.getById(this.optionalEditId).subscribe({
        next: (res) => {
          this.importForm.controls.name.setValue(res.name)
          this.importForm.controls.link.setValue(res.link)
          this.optionalToken = res.token
        }, error: () => {
          this.toastrService.error("Couldn't fetch Calendar data")
        }
      })
    }
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
      id: this.optionalEditId,
      name: this.importForm.controls.name.value,
      link: this.importForm.controls.link.value,
      token: this.optionalToken
    }
      this.calendarReferenceService.upsertCalendar(toImport).subscribe({
        next: () => {
          this.router.navigate(['calendar']);
          if(this.editMode) {
            this.toastrService.success("Calendar edited")
          } else {
            this.toastrService.success("Created Calendar")
          }
        },
        error: err => {
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


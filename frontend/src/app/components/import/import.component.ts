import { Component, OnInit } from '@angular/core';
import { CalendarReferenceDto } from "../../dtos/calendar-reference-dto";
import { CalendarReferenceService } from "../../services/calendar.reference.service";
import { Router } from "@angular/router";
import { FormGroup, UntypedFormBuilder, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportComponent implements OnInit {

  importForm: FormGroup;
  isURLImport = true;
  selectedFile: File | null = null;

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
    this.initForm();
    if (this.editMode) {
      this.calendarReferenceService.getById(this.optionalEditId).subscribe({
        next: (res) => {
          this.importForm.controls.name.setValue(res.name);
          this.importForm.controls.color.setValue(res.color);
          this.optionalToken = res.token;

          if (res.icalData) {
            this.isURLImport = false;
            this.selectedFile = new File([new Blob([res.icalData])], "imported_calendar.ics", { type: "text/calendar" });
            this.importForm.get('file').setValue(this.selectedFile.name);
            this.importForm.get('file').setValue(this.selectedFile.name);
            this.importForm.get('file').enable();
            this.importForm.get('link').clearValidators();
            this.importForm.get('link').setValue('');
            this.importForm.get('importSource').setValue('file');
          } else {
            this.importForm.controls.link.setValue(res.link);
            this.importForm.get('importSource').setValue('url');
          }
          this.updateFormValidators();
        }, error: () => {
          this.toastrService.error("Couldn't fetch Calendar data");
        }
      });
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

  initForm(): void {
    const randomColor = Math.floor(Math.random()*16777215).toString(16);
    this.importForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      link: [''],
      importSource: ['url', [Validators.required]],
      file: [{ value: null, disabled: true }],
      color: ['#' + randomColor],
    });
    this.updateFormValidators();
  }

  updateFormValidators(): void {
    if (this.isURLImport) {
      this.importForm.get('link').setValidators([Validators.required]);
      this.importForm.get('link').updateValueAndValidity();
      this.importForm.get('file').clearValidators();
      this.importForm.get('file').disable();
    } else {
      this.importForm.get('file').setValidators([Validators.required]);
      this.importForm.get('file').enable();
      this.importForm.get('link').clearValidators();
      this.importForm.get('link').updateValueAndValidity();
    }
  }


  onImportSourceChange(): void {
    this.isURLImport = this.importForm.controls.importSource.value === 'url';

    if (this.isURLImport) {
      this.importForm.get('link').reset();
    } else {
      this.importForm.get('file').reset();
      this.selectedFile = null;
    }
    this.updateFormValidators();
  }

  onFileSelected(event: Event): void {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;

    if (fileList) {
      const file = fileList[0];

      if (file.type === "text/calendar" || file.name.endsWith('.ics')) {
        this.selectedFile = file;
        this.importForm.get('file').setValue(this.selectedFile ? this.selectedFile.name : null);
      } else {
        this.toastrService.error("Only iCal (.ics) files are allowed!");
        this.importForm.get('file').setValue(null);
        this.selectedFile = null;
      }
    }
  }

  importCalendar() {
    this.submitted = true;

    if (this.isURLImport) {
      const toImport: CalendarReferenceDto = {
        id: this.optionalEditId,
        name: this.importForm.controls.name.value,
        link: this.importForm.controls.link.value,
        color: this.importForm.controls.color.value,
        token: this.optionalToken,
        icalData: null
      }
      this.calendarReferenceService.upsertCalendar(toImport).subscribe({
        next: () => {
          this.router.navigate(['calendar']);
          if (this.editMode) {
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
      });
    } else if (this.selectedFile) {
      const formData = new FormData();
      formData.append('name', this.importForm.controls.name.value);
      formData.append('file', this.selectedFile, this.selectedFile.name);
      formData.append('color', this.importForm.controls.color.value);
      if (this.optionalToken) {
        formData.append('token', this.optionalToken);
      }
      formData.append('link', '');

      this.calendarReferenceService.upsertCalendarFile(formData).subscribe({
        next: () => {
          this.router.navigate(['calendar']);
          this.toastrService.success("File uploaded successfully");
        },
        error: err => {
          this.defaultServiceErrorHandling(err);
          this.toastrService.error("Could not import: " + this.errorMessage);
        }
      });
    }
  }
}


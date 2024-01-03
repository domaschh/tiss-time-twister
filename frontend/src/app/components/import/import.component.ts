import { Component, OnInit } from '@angular/core';
import { CalendarReferenceDto } from "../../dtos/calendar-reference-dto";
import { CalendarReferenceService } from "../../services/calendar.reference.service";
import { Router } from "@angular/router";
import { FormControl, FormGroup, UntypedFormBuilder, Validators } from "@angular/forms";
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

  initForm(): void {
    this.importForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      link: [''],
      importSource: ['url', [Validators.required]],
      file: [{ value: null, disabled: true }]
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
      this.selectedFile = fileList[0];
      this.importForm.get('file').setValue(this.selectedFile ? this.selectedFile.name : null);
    }
  }

  importCalendar() {
    this.submitted = true;

    if (this.isURLImport) {
      const toImport: CalendarReferenceDto = {
        id: this.optionalEditId,
        name: this.importForm.controls.name.value,
        link: this.importForm.controls.link.value,
        token: this.optionalToken
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

      if (this.optionalToken) {
        formData.append('token', this.optionalToken);
      }

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


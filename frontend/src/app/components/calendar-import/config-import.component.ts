import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {HttpClient} from "@angular/common/http";
import {ConfigurationDto} from "../../dtos/configuration-dto";
import {ToastrService} from "ngx-toastr";
import {ConfigurationService} from "../../services/configuration.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {NgxFileDropEntry} from "ngx-file-drop";

@Component({
  selector: 'app-config-import',
  templateUrl: './config-import.component.html',
  styleUrls: ['./config-import.component.scss']
})
export class ConfigImportComponent implements OnInit {
  configUrl: string;
  @Input() calendars: CalendarReferenceDto[];
  selectedCal: number
  byLink: boolean;
  filename: string;

  selectedConfigurationDto: ConfigurationDto;
  @Output() addedConfiguration: EventEmitter<ConfigurationDto> = new EventEmitter<ConfigurationDto>();

  constructor(private readonly http: HttpClient, private readonly toastrService: ToastrService, private readonly configService: ConfigurationService, private readonly modal: NgbModal, private readonly calendarService: CalendarReferenceService) {
    console.log(this.calendars)
  }

  ngOnInit(): void {

  }

  import() {
    if (this.byLink) {
      //http://localhost:8080/api/v1/calendar/?/1
      //return this.httpClient.post<CalendarReferenceDto>(this.messageBaseUri + "/" + selectedCal + "/" + configuration, null);
      return this.http.post<ConfigurationDto>(this.configUrl.replace("?", "" + this.selectedCal), null).subscribe({
        next: () => {
          this.toastrService.success("Imported Configuration")
        }, error: () => {
          this.toastrService.error("Failed to import")
        }
      })
    } else {
      if (this.selectedConfigurationDto && this.selectedCal) {
        return this.configService.createConfiguration(-1, this.selectedConfigurationDto).subscribe({
          next: (created) => {
            this.calendarService.addToCalendar(this.selectedCal, created.id).subscribe({
              next: (added) => {
                this.toastrService.success("Added configuration to calendar")
                this.addedConfiguration.emit(created)
                this.modal.dismissAll()
              }, error: () => {
                this.toastrService.error("Failed to add configuration to calendar")
              }
            })
          }, error: () => {
            this.toastrService.error("Failed to create Configuration")
          }
        })
      } else {
        this.toastrService.error("No File uploaded")
      }
    }
  }

  closeModal() {
    this.modal.dismissAll()
  }

  uploadFile(event: any) {
    event.preventDefault()
    const file: File = event.target.files[0];
    if (file) {
      console.log(file.type)
      if (file.type != "application/json") {
        this.toastrService.error("Please upload a valid json")
        return
      }
      const fileReader = new FileReader();
      fileReader.onload = (e) => {
        try {
          const jsonData = JSON.parse(fileReader.result as string);
          this.filename = file.name;
          this.selectedConfigurationDto = jsonData as ConfigurationDto;
        } catch (err) {
          console.error('Error parsing JSON:', err);
          this.toastrService.error("Error uploading file")
        }
      };

      fileReader.readAsText(file);
    }
  }

  public files: NgxFileDropEntry[] = [];

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          console.log(droppedFile.relativePath, file);
          if (file) {
            console.log(file.type)
            if (file.type != "application/json") {
              this.toastrService.error("Please upload a valid json")
              return
            }
            const fileReader = new FileReader();
            fileReader.onload = (e) => {
              try {
                const jsonData = JSON.parse(fileReader.result as string);
                this.filename = file.name;
                this.selectedConfigurationDto = jsonData as ConfigurationDto;
              } catch (err) {
                console.error('Error parsing JSON:', err);
                this.toastrService.error("Error uploading file")
              }
            };
            fileReader.readAsText(file);
          }
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event) {
    console.log(event);
  }

  public fileLeave(event) {
    console.log(event);
  }
}

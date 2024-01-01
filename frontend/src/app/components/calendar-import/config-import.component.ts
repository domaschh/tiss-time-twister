import {Component, Input} from '@angular/core';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {HttpClient} from "@angular/common/http";
import {ConfigurationDto} from "../../dtos/configuration-dto";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-config-import',
  templateUrl: './config-import.component.html',
  styleUrls: ['./config-import.component.scss']
})
export class ConfigImportComponent {
  configUrl: string;
  @Input() calendars: CalendarReferenceDto[];
  selectedCal: number

  constructor(private readonly http: HttpClient, private readonly toastrService: ToastrService) {
    console.log(this.calendars)
  }
  import() {
    //http://localhost:8080/api/v1/calendar/?/1
    //return this.httpClient.post<CalendarReferenceDto>(this.messageBaseUri + "/" + selectedCal + "/" + configuration, null);
    return this.http.post<ConfigurationDto>(this.configUrl.replace("?", "" + this.selectedCal), null).subscribe({
      next: () => {
        this.toastrService.success("Imported Configuration")
      }, error: () => {
        this.toastrService.error("Failed to import")
      }
    })
  }

  closeModal() {

  }
}

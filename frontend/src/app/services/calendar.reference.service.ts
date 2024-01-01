import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Globals } from "../global/globals";
import { CalendarReferenceDto } from "../dtos/calendar-reference-dto";
import { Observable, catchError, of } from 'rxjs';
import { Configuration } from '../dtos/Configuration';
import { ConfigurationDto } from '../dtos/configuration-dto';

@Injectable({
  providedIn: 'root'
})
export class CalendarReferenceService {

  private messageBaseUri: string = this.globals.backendUri + '/calendar';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  upsertCalendar(dto: CalendarReferenceDto) {
    console.log(dto)
    return this.httpClient.put<CalendarReferenceDto>(this.messageBaseUri, dto);
  }

  deleteCalendar(id: number) {
    return this.httpClient.delete(this.messageBaseUri + "/" + id);
  }

  getById(id: number) {
    return this.httpClient.get<CalendarReferenceDto>(this.messageBaseUri + '/' + id);
  }

  getIcalFromToken(token: string) {
    return this.httpClient.get(this.messageBaseUri + "/export/" + token, { responseType: 'text' });
  }

  getAll(): Observable<CalendarReferenceDto[]> {
    return this.httpClient.get<CalendarReferenceDto[]>(this.messageBaseUri)
      .pipe(
        catchError(error => {
          return of([]);
        })
      );
  }

  getConfigurationPreview(calendarId: number, config: ConfigurationDto) {
    return this.httpClient.post(this.messageBaseUri + "/preview/" + calendarId, [config], { responseType: 'text'});
  }

  addToCalendar(selectedCal: number, configuration: number) {
    return this.httpClient.post<CalendarReferenceDto>(this.messageBaseUri + "/" + selectedCal + "/" + configuration, null);
  }

  removeFromCalendar(selectedCal: number, configuration: number) {
    return this.httpClient.delete<CalendarReferenceDto>(this.messageBaseUri + "/" + selectedCal + "/" + configuration);
  }
}

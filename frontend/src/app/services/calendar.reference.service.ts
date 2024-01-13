import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { Globals } from "../global/globals";
import { CalendarReferenceDto } from "../dtos/calendar-reference-dto";
import { Observable, catchError, of } from 'rxjs';
import { ConfigurationDto } from '../dtos/configuration-dto';
import {TagDto} from "../dtos/tag-dto";

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

  upsertCalendarFile(formData: FormData): Observable<any> {
    return this.httpClient.put(this.messageBaseUri + '/file', formData);
  }

  deleteCalendar(id: number) {
    return this.httpClient.delete(this.messageBaseUri + "/" + id);
  }

  getById(id: number) {
    return this.httpClient.get<CalendarReferenceDto>(this.messageBaseUri + '/' + id);
  }

  getIcalFileFromToken(token: string, tags: TagDto[]) {
    let params = new HttpParams();
    for (let i = 0; i < tags.length; i++) {
      params = params.append('tag', tags[i].id);
    }
    return this.httpClient.get(this.getIcalLinkFromToken(token, []), { params: params, responseType: 'text' });
  }

  getIcalLinkFromToken(token: string, tags: TagDto[]){
    let uri = this.messageBaseUri + "/export/" + token;
    if (tags.length === 0) {
      return uri;
    }
    let params = new HttpParams();
    for (let i = 0; i < tags.length; i++) {
      params = params.append('tag', tags[i].id);
    }
    return uri + '?' + params.toString();
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
    return this.httpClient.post(this.messageBaseUri + "/preview/" + calendarId, [config], { responseType: 'text' });
  }

  addToCalendar(selectedCal: number, configuration: number) {
    return this.httpClient.post<CalendarReferenceDto>(this.messageBaseUri + "/" + selectedCal + "/" + configuration, null);
  }

  removeFromCalendar(selectedCal: number, configuration: number) {
    return this.httpClient.delete<CalendarReferenceDto>(this.messageBaseUri + "/" + selectedCal + "/" + configuration);
  }
}

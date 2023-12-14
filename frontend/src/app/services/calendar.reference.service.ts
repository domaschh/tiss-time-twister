import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Globals} from "../global/globals";
import {CalendarReferenceDto} from "../dtos/calendar-reference-dto";

@Injectable({
  providedIn: 'root'
})
export class CalendarReferenceService {

  private messageBaseUri: string = this.globals.backendUri + '/calendar';


  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  importCalendar(dto: CalendarReferenceDto) {
    console.log(dto)
    return this.httpClient.put<CalendarReferenceDto>(this.messageBaseUri, dto);
  }

  getById(id: number) {
    return this.httpClient.get<CalendarReferenceDto>(this.messageBaseUri + '/' + id);
  }
}

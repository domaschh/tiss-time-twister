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


  private cal1 : String = "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:Calendar 1\nCALSCALE:GREGORIAN\nBEGIN:VEVENT\nSUMMARY:JF\nDESCRIPTION:Online\nCATEGORIES:HOLIDAY\nDTSTART:20231215T130000Z\nDTEND:20231215T140000Z\nUID:unique-event-id-1\nSEQUENCE:0\nSTATUS:CONFIRMED\nTRANSP:OPAQUE\nEND:VEVENT\nBEGIN:VEVENT\nSUMMARY:MR2\nDESCRIPTION:Online\nCATEGORIES:HOLIDAY\nDTSTART:20231221T160000Z\nDTEND:20231221T170000Z\nUID:unique-event-id-2\nSEQUENCE:0\nSTATUS:CONFIRMED\nTRANSP:OPAQUE\nEND:VEVENT\nEND:VCALENDAR";
  private cal2 : String = "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:Calendar 2\nCALSCALE:GREGORIAN\nBEGIN:VEVENT\nSUMMARY: Uebung\nDESCRIPTION:HB12\nCATEGORIES:HOLIDAY\nDTSTART:20231218T090000Z\nDTEND:20231218T110000Z\nUID:unique-event-id-4\nSEQUENCE:0\nSTATUS:CONFIRMED\nTRANSP:OPAQUE\nEND:VEVENT\nBEGIN:VEVENT\nSUMMARY:Weihnachten\nDESCRIPTION:Online\nCATEGORIES:HOLIDAY\nDTSTART:20231224T120000Z\nDTEND:20231224T130000Z\nUID:unique-event-id-5\nSEQUENCE:0\nSTATUS:CONFIRMED\nTRANSP:OPAQUE\nEND:VEVENT\nEND:VCALENDAR";

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
        //return dummy data because the backend is not working currently
        console.log("Request failed returning dummy data");
        console.log(error)
        return of([]);
      })
    );
  }

  addToCalendar(selectedCal: number, configuration: number) {
    return this.httpClient.post<CalendarReferenceDto>(this.messageBaseUri + "/" +selectedCal + "/" + configuration, null);
  }

  removeFromCalendar(selectedCal: number, configuration: number) {
    return this.httpClient.delete<CalendarReferenceDto>(this.messageBaseUri + "/" +selectedCal + "/" + configuration);
  }
}

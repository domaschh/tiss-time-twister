import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Globals} from "../global/globals";
import {CustomEventDto} from "../dtos/custom-event-dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private messageBaseUri: string = this.globals.backendUri + '/event';


  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  createEvent(dto: CustomEventDto): Observable<CustomEventDto> {
    console.log(dto);
    return this.httpClient.post<CustomEventDto>(this.messageBaseUri, dto);
  }

  updateEvent(dto: CustomEventDto): Observable<CustomEventDto> {
    console.log(dto);
    return this.httpClient.put<CustomEventDto>(this.messageBaseUri + '/' + dto.id, dto);
  }

  getEventById(id: string): Observable<CustomEventDto> {
    console.log('Get event ' + id);
    return this.httpClient.get<CustomEventDto>(this.messageBaseUri + '/' + id);
  }

  deleteEvent(id: number | string): Observable<object> {
    if (id.toString().includes('customEvent')) {
      id = id.toString().split('_')[1];
    }
    return this.httpClient.delete(this.messageBaseUri + '/' + id);
  }
}

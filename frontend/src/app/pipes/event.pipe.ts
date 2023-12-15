import { Pipe, PipeTransform } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { MyCalendarEvent } from '../dtos/Calendar';

@Pipe({
  name: 'eventPipe',
  standalone: true,
  pure: false
})
export class EventPipePipe implements PipeTransform {

  transform(allEvents: MyCalendarEvent[]): CalendarEvent[] {
    //TODO: include Configurations in filtering or changing data?
    if(allEvents.length == 0)
    return allEvents;
    return allEvents.filter(e => e.calendar.isActive);
  }

}

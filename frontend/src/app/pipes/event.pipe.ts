import { Pipe, PipeTransform } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';

@Pipe({
  name: 'eventPipe',
  standalone: true,
  pure: false
})
export class EventPipePipe implements PipeTransform {

  transform(allEvents: CalendarEvent[]): CalendarEvent[] {
    if(allEvents.length == 0)
    return allEvents;
    return allEvents.filter(e => e.calendar.isActive);
  }

}

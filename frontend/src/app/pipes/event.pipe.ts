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
    return allEvents.filter(e => e.calendar.isActive);
  }
}

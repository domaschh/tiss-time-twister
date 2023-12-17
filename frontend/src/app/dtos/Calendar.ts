import { CalendarEvent } from "angular-calendar"

export class Calendar{
  constructor(
    public name: string,
    public id: number,
    public color: string,
    public isActive: boolean,
    public events?: MyCalendarEvent[]
  ) {}
}

export interface MyCalendarEvent extends CalendarEvent{
  calendar?: Calendar;
}

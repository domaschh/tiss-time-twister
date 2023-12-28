import { CalendarEvent } from "angular-calendar"

export class Calendar{
  constructor(
    public name: string,
    public token: string,
    public link: string,
    public id: number,
    public color: string,
    public isActive: boolean,
    public events?: MyCalendarEvent[]
  ) {}
}

export interface MyCalendarEvent extends CalendarEvent{
  location: string,
  categories: string,
  calendar?: Calendar;
}

export interface EventCalendar {
  id: number;
  name: string;
}

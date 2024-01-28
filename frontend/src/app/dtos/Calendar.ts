import {CalendarEvent} from "angular-calendar"
import {ConfigurationDto} from "./configuration-dto";

export class Calendar{
  constructor(
    public name: string,
    public token: string,
    public link: string,
    public id: number,
    public color: string,
    public isActive: boolean,
    public events?: MyCalendarEvent[],
    public configs?: ConfigurationDto[],
    public sourceError?: boolean
  ) {}
}

export interface MyCalendarEvent extends CalendarEvent{
  location: string,
  categories: string,
  description?: string,
  calendar?: Calendar;
  backgroundColor?: string;
}

export interface EventCalendar {
  id: number;
  name: string;
}

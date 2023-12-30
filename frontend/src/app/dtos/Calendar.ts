import { CalendarEvent } from "angular-calendar"
import {CalendarReferenceDto} from "./calendar-reference-dto";
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
    public configs?: ConfigurationDto[]
  ) {}
}

export interface MyCalendarEvent extends CalendarEvent{
  location: string,
  categories: string,
  calendar?: Calendar;
  backgroundColor?: string;
}

export interface EventCalendar {
  id: number;
  name: string;
}

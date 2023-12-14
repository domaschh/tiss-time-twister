export class Calendar{
  constructor(
    public name: string,
    public id: number,
    public color: string,
    public isActive: boolean,
    public events?: MyCalendarEvent[]
  ) {}
}

export interface MyCalendarEvent{
   title: string,
   id?: number,
   start: Date,
   end?: Date,
   color?: string
}

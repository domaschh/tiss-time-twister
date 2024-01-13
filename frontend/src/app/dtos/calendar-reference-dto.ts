import { ConfigurationDto } from "./configuration-dto";


export class CalendarReferenceDto {
  id: number | null;
  name: string;
  link?: string;
  token: string | null;
  color?: string;
  configurations?: ConfigurationDto[];
  enabledDefaultConfigurations?: number;
  icalData?: string;
}

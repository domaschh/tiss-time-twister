import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConfigurationDto} from "../dtos/configuration-dto";
import {Globals} from "../global/globals";

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {
  private messageBaseUri: string = this.globals.backendUri + '/configuration';

  constructor(private readonly httpClient: HttpClient, private readonly globals: Globals) { }

  getAll(){
      return this.httpClient.get<ConfigurationDto[]>(this.messageBaseUri + '/all');
  }

  getAllPublic() {
    return this.httpClient.get<ConfigurationDto[]>(this.messageBaseUri + "/allPublic")
  }

  createConfiguration(calendarId: number, configurationDto: ConfigurationDto) {
    return this.httpClient.put<ConfigurationDto>(this.messageBaseUri, configurationDto)
  }
  remove(configurationId: number) {
    return this.httpClient.delete(this.messageBaseUri + '/' + configurationId)
  }
}

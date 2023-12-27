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
    return null;
  }

  getAllPublic() {
    return this.httpClient.get<ConfigurationDto[]>(this.messageBaseUri + "/allPublic")
  }
}

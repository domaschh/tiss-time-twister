import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Globals} from "../global/globals";
import {catchError, Observable, of} from "rxjs";
import {TagDto} from "../dtos/tag-dto";
import {ConfigurationDto} from "../dtos/configuration-dto";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private messageBaseUri: string = this.globals.backendUri + '/tag';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getAll(): Observable<TagDto[]> {
    return this.httpClient.get<TagDto[]>(this.messageBaseUri)
        .pipe(
            catchError(error => {
              return of([]);
            })
        );
  }

  createTag(tag: TagDto): Observable<TagDto> {
    return this.httpClient.post<TagDto>(this.messageBaseUri, tag);
  }

  deleteTag(tagId: number) {
    return this.httpClient.delete<ConfigurationDto[]>(this.messageBaseUri + '/' + tagId);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';
import { EventListResponse } from '../../models/event.model';


@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private _httpClient = inject(HttpClient)
  private _baseUrl = environment.baseUrl + '/events'

  public getEventsList(): Observable<EventListResponse> {
    return this._httpClient.get<EventListResponse>(this._baseUrl);
  }
}

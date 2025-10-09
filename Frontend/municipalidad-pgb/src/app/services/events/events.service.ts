import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';
import { EventListResponse, EventListDataResponse, EventByIdResponse, EventRequestDTO } from '../../models/event.model';


@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private _httpClient = inject(HttpClient)
  private _baseUrl = environment.baseUrl + '/events'

  public getEventsList(): Observable<EventListResponse> {
    return this._httpClient.get<EventListResponse>(this._baseUrl);
  }

   public getEventListPaginated(page: number): Observable<EventListDataResponse> {
      return this._httpClient.get<EventListDataResponse>(this._baseUrl+`/paginado?page=${page}`);
    }
  
    public getEventById(id: number): Observable<EventByIdResponse> {
      return this._httpClient.get<EventByIdResponse>(`${this._baseUrl}/${id}`);
    }
  
     public createEvent(event: EventRequestDTO): Observable<EventRequestDTO> {
        return this._httpClient.post<EventRequestDTO>(this._baseUrl, event);
      }
  
      public updateEvent(event: EventRequestDTO, id: number): Observable<EventRequestDTO> {
        return this._httpClient.put<EventRequestDTO>(`${this._baseUrl}/${id}`, event);
      }
  
    public deleteEvent(id: number): Observable<String> {
      return this._httpClient.delete<String>(`${this._baseUrl}/${id}`);
    }
}

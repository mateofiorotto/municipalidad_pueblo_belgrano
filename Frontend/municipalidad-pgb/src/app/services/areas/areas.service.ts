import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { AreaListResponse, AreaResponseDTO, AreaListPaginatedResponse, AreaByIdResponse } from '../../models/area.model';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AreasService {

  private _httpClient = inject(HttpClient)
  private _baseUrl = environment.baseUrl + '/areas'

  public getAreasList(): Observable<AreaListResponse> {
    return this._httpClient.get<AreaListResponse>(this._baseUrl);
  }

  public getAreasListPaginated(page: number): Observable<AreaListPaginatedResponse> {
    return this._httpClient.get<AreaListPaginatedResponse>(this._baseUrl + `/paginado?page=${page}`);
  }

  public getAreaById(id: number): Observable<AreaByIdResponse> {
    return this._httpClient.get<AreaByIdResponse>(`${this._baseUrl}/${id}`);
  }

  public createArea(area: AreaResponseDTO): Observable<AreaResponseDTO> {
    return this._httpClient.post<AreaResponseDTO>(this._baseUrl, area);
  }

  public updateArea(area: AreaResponseDTO, id: number): Observable<AreaResponseDTO> {
    return this._httpClient.put<AreaResponseDTO>(`${this._baseUrl}/${id}`, area);
  }

  public deleteArea(id: number): Observable<String> {
    return this._httpClient.delete<String>(`${this._baseUrl}/${id}`);
  }
}

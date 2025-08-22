import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { AreaListResponse, AreaResponseDTO } from '../../models/area.model';
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
}

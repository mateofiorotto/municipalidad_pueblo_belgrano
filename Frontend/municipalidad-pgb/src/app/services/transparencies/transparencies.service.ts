import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { TransparencyListResponse, TransparencyByIdResponse, TransparencyRequestDTO } from '../../models/transparency.model';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransparenciesService {
  private _httpClient = inject(HttpClient);
  private _baseUrl = environment.baseUrl + '/transparencies';

   public getTransparenciesList(page: number): Observable<TransparencyListResponse> {
    return this._httpClient.get<TransparencyListResponse>(this._baseUrl+`?page=${page}`);
  }

  public getTransparencyById(id: number): Observable<TransparencyByIdResponse> {
    return this._httpClient.get<TransparencyByIdResponse>(`${this._baseUrl}/${id}`);
  }

   public createTransparency(news: TransparencyRequestDTO): Observable<TransparencyRequestDTO> {
      return this._httpClient.post<TransparencyRequestDTO>(this._baseUrl, news);
    }

    public updateTransparency(news: TransparencyRequestDTO, id: number): Observable<TransparencyRequestDTO> {
      return this._httpClient.put<TransparencyRequestDTO>(`${this._baseUrl}/${id}`, news);
    }

  public deleteTransparency(id: number): Observable<String> {
    return this._httpClient.delete<String>(`${this._baseUrl}/${id}`);
  }

  public getTransparencyTypes(): Observable<string[]> {
    return this._httpClient.get<string[]>(`${this._baseUrl}/types`);
  }
}

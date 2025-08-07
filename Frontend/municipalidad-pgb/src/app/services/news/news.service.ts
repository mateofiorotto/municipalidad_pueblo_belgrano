import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { NewsByIdResponse, NewsListResponse } from '../../models/news.models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private _httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/news';

   public getNewsList(): Observable<NewsListResponse> {
    return this._httpClient.get<NewsListResponse>(this.baseUrl);
  }

  public getNewsById(id: number): Observable<NewsByIdResponse> {
    return this._httpClient.get<NewsByIdResponse>(`${this.baseUrl}/${id}`);
  }
}

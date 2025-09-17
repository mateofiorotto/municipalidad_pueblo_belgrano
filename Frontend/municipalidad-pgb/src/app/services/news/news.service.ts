import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { NewsByIdResponse, NewsListResponse, NewsThreeLastResponse, NewsRequestDTO } from '../../models/news.models';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private _httpClient = inject(HttpClient);
  private _baseUrl = environment.baseUrl + '/news';

   public getNewsList(page: number): Observable<NewsListResponse> {
    return this._httpClient.get<NewsListResponse>(this._baseUrl+`?page=${page}`);
  }

  public getNewsById(id: number): Observable<NewsByIdResponse> {
    return this._httpClient.get<NewsByIdResponse>(`${this._baseUrl}/${id}`);
  }

  public getLastThreeNews(): Observable<NewsThreeLastResponse> {
    return this._httpClient.get<NewsThreeLastResponse>(`${this._baseUrl}/last-three-news`);
  }

   public createNews(news: NewsRequestDTO): Observable<NewsRequestDTO> {
      return this._httpClient.post<NewsRequestDTO>(this._baseUrl, news);
    }

    public updateNews(news: NewsRequestDTO, id: number): Observable<NewsRequestDTO> {
      return this._httpClient.put<NewsRequestDTO>(`${this._baseUrl}/${id}`, news);
    }

  public deleteNews(id: number): Observable<String> {
    return this._httpClient.delete<String>(`${this._baseUrl}/${id}`);
  }
}

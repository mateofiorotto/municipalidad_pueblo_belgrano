import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { CategoryListResponse, CategoryResponseDTO } from '../../models/category.model';
import { inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  private _httpClient = inject(HttpClient)
  private _baseUrl = environment.baseUrl + '/categories'

  public getCategoriesList(): Observable<CategoryListResponse> {
    return this._httpClient.get<CategoryListResponse>(this._baseUrl);
  }
}

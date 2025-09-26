import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { CategoryListResponse, CategoryResponseDTO, CategoryListPaginatedResponse, CategoryByIdResponse } from '../../models/category.model';
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
  
    public getCategoriesListPaginated(page: number): Observable<CategoryListPaginatedResponse> {
      return this._httpClient.get<CategoryListPaginatedResponse>(this._baseUrl + `/paginado?page=${page}`);
    }
  
    public getCategoryById(id: number): Observable<CategoryByIdResponse> {
      return this._httpClient.get<CategoryByIdResponse>(`${this._baseUrl}/${id}`);
    }
  
    public createCategory(Category: CategoryResponseDTO): Observable<CategoryResponseDTO> {
      return this._httpClient.post<CategoryResponseDTO>(this._baseUrl, Category);
    }
  
    public updateCategory(Category: CategoryResponseDTO, id: number): Observable<CategoryResponseDTO> {
      return this._httpClient.put<CategoryResponseDTO>(`${this._baseUrl}/${id}`, Category);
    }
  
    public deleteCategory(id: number): Observable<String> {
      return this._httpClient.delete<String>(`${this._baseUrl}/${id}`);
    }
}

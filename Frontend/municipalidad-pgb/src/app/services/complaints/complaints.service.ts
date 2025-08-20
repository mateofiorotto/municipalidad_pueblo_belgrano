import { Injectable } from '@angular/core';
import { ComplaintByIdResponse, ComplaintRequestDTO, ComplaintUpdateDTO } from '../../models/complaint.model';
import { ComplaintListResponse } from '../../models/complaint.model';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  private _httpClient = inject(HttpClient)
  private _baseUrl = environment.baseUrl + '/complaints'

  public createComplaint(complaint: ComplaintRequestDTO): Observable<ComplaintRequestDTO> {
    return this._httpClient.post<ComplaintRequestDTO>(this._baseUrl+'/save', complaint);
  }

  public getComplaintsList(page: number): Observable<ComplaintListResponse> {
    return this._httpClient.get<ComplaintListResponse>(`${this._baseUrl}?page=${page}`);
  }

  public getComplaintsListByArea(page: number, areaId: number): Observable<ComplaintListResponse> {
    const params = new HttpParams()
    .set('area_id', areaId.toString())
    .set('page', page.toString());

    return this._httpClient.get<ComplaintListResponse>(`${this._baseUrl}/area`, { params });
  }

  public getComplaintById(id: number): Observable<ComplaintByIdResponse>{
    return this._httpClient.get<ComplaintByIdResponse>(`${this._baseUrl}/${id}`);
  }

  public updateComplaint(complaint: ComplaintUpdateDTO, id: number): Observable<ComplaintUpdateDTO> {
    return this._httpClient.put<ComplaintUpdateDTO>(`${this._baseUrl}/${id}`, complaint);
  }

  public deleteComplaint(id: number): Observable<void> {
    return this._httpClient.delete<void>(`${this._baseUrl}/${id}`);
  }
}

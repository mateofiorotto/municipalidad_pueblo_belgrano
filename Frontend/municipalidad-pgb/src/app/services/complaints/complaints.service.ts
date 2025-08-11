import { Injectable } from '@angular/core';
import { ComplaintRequestDTO } from '../../models/complaint.model';
import { ComplaintListResponse } from '../../models/complaint.model';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  private _httpClient = inject(HttpClient)
  private _baseUrl = 'http://localhost:8080/complaints'

  public createComplaint(complaint: ComplaintRequestDTO): Observable<ComplaintRequestDTO> {
    return this._httpClient.post<ComplaintRequestDTO>(this._baseUrl, complaint);
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
}

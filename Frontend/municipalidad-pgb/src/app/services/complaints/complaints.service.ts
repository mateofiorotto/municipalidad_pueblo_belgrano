import { Injectable } from '@angular/core';
import { ComplaintRequest } from '../../models/complaint.model';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  private _httpClient = inject(HttpClient)
  private _baseUrl = 'http://localhost:8080/complaints'

  public createComplaint(complaint: ComplaintRequest): Observable<ComplaintRequest> {
    return this._httpClient.post<ComplaintRequest>(this._baseUrl, complaint);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HealthService {

  private _httpClient = inject(HttpClient);
  private _baseUrl = environment.baseUrl + '/health';

   public getHealthStatus(): Observable<boolean> {
    return this._httpClient.get<String>(this._baseUrl).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }
}

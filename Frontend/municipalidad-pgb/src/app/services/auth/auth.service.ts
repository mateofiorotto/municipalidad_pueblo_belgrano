import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { UserLoginResponse } from '../../models/user.model';
import { shareReplay } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/auth/login';

  public login(username: string, password: string) {
    return this._httpClient.post<UserLoginResponse>(this.baseUrl, { username, password })
    .pipe(shareReplay());
  }

  public isAuthenticated(): boolean {
    return !!localStorage.getItem('auth_token');
  }
  
}

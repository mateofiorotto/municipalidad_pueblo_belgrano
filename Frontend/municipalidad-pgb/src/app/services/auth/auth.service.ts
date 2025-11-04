import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { UserLoginResponse } from '../../models/user.model';
import { jwtDecode } from 'jwt-decode';
import Swal from 'sweetalert2';
import { Token } from '../../models/token.model';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _httpClient = inject(HttpClient);
  private _router = inject(Router);
  private _baseUrl = environment.baseUrl + '/auth/login';

  public login(username: string, password: string, captcha: string): Observable<UserLoginResponse> {
    return this._httpClient.post<UserLoginResponse>(this._baseUrl, {
      username,
      password,
      captcha
    });
  }

  public logout(): void {
    localStorage.removeItem('auth_token');

    this._router.navigate(['/login']).then(() => {
      Swal.fire({
        icon: 'warning',
        title: 'Sesión Cerrada',
        text: 'Tu sesión ha sido cerrada',
        showConfirmButton: true
      });
    });
  }

  public getUsername(){
    if(localStorage.getItem('auth_token') !== null) {
      let jwtToken: string | any = localStorage.getItem('auth_token');

      //decodificar
      jwtDecode(jwtToken);

      return jwtDecode(jwtToken).sub;
    };

    return false;
  }

  public isLogged(): boolean {
    return localStorage.getItem('auth_token') !== null;
  }

  public isTokenExpired(token: Token): boolean {
    return Date.now() >= token.exp * 1000;
  }

  public isTokenValidAndNotExpired(): boolean {
    const tokenString = localStorage.getItem('auth_token');
    if (!tokenString) return false;

    try {
      const decoded = jwtDecode<Token>(tokenString);
      if (this.isTokenExpired(decoded)) {
        this.logout();
        return false;
      }
      return true;
    } catch {
      this.logout();
      return false;
    }
  }

  public isAuthenticated(): boolean {
    return this.isTokenValidAndNotExpired();
  }

  public isAuthorized(allowedRoles: string[]): boolean {
    const tokenString = localStorage.getItem('auth_token');
    if (!tokenString) return false;

    try {
      const decodedToken = jwtDecode<Token>(tokenString);

      if (this.isTokenExpired(decodedToken)) {
        this.logout();
        return false;
      }

      const rolesArray = decodedToken.authorities.split(',');

      return rolesArray.some((role) => allowedRoles.includes(role));
    } catch {
      this.logout();
      return false;
    }
  }
}

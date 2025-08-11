import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const idToken = localStorage.getItem("auth_token");

  if (idToken) {
    const cloned = req.clone({
      headers: req.headers.set("Authorization", "Bearer " + idToken)
    });
    return next(cloned);
  }
  return next(req);
};

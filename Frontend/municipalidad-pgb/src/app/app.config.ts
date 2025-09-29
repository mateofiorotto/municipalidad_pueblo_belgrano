import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  provideHttpClient,
  withFetch,
  withInterceptors,
} from '@angular/common/http';
import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth/auth.interceptor';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { TranslateMaterial } from './core/translate-material/translate-material';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Aos } from 'aos';
import { LOCALE_ID } from '@angular/core';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),

    provideRouter(routes),

    provideHttpClient(withFetch(), withInterceptors([authInterceptor])),

    { provide: MatPaginatorIntl, useClass: TranslateMaterial },

    { provide: LOCALE_ID, useValue: 'es' },
    
    importProvidersFrom([SweetAlert2Module.forRoot()]),
  ],
};

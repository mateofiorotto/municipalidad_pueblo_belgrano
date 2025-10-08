import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderAdminComponent } from './components/header-admin/header-admin.component';
import { AuthService } from './services/auth/auth.service';
import { HealthService } from './services/health/health.service';
import { map } from 'rxjs';
import * as AOS from 'aos';
import 'aos/dist/aos.css';
import { LoaderComponent } from './components/loader/loader.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    HeaderComponent,
    FooterComponent,
    HeaderAdminComponent,
    LoaderComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  private _authService = inject(AuthService);
  private _healthService = inject(HealthService);

  public loading = true;
  public apiIsUp = false;
  public router = inject(Router);

  title = 'municipalidad-pgb';

  ngAfterViewInit(): void {
    AOS.init({
    });
  }

  ngOnInit(): void {
  this._authService.isTokenValidAndNotExpired();

  setInterval(() => {
    this._authService.isTokenValidAndNotExpired();
  }, 60000);

  this._healthService.getHealthStatus()
    .pipe(
      map((isUp) => {
        return new Promise<boolean>((resolve) => setTimeout(() => resolve(isUp), 1000));
      })
    )
    .subscribe(async (promise) => {
      this.apiIsUp = await promise;
      this.loading = false;
    });
}
}

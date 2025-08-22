import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderAdminComponent } from './components/header-admin/header-admin.component';
import { AuthService } from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    HeaderComponent,
    FooterComponent,
    HeaderAdminComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  private _authService = inject(AuthService);
  public router = inject(Router);
  title = 'municipalidad-pgb';

  ngOnInit(): void {
    this._authService.isTokenValidAndNotExpired();

    setInterval(() => {
      this._authService.isTokenValidAndNotExpired();
    }, 60000);
  }
}

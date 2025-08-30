import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  protected _authService = inject(AuthService);

  public logout() {
    setTimeout(() => {
      this._authService.logout();
    });
  }
}

import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-header-admin',
  imports: [RouterModule],
  templateUrl: './header-admin.component.html',
  styleUrl: './header-admin.component.css',
})
export class HeaderAdminComponent {
  protected _authService = inject(AuthService);

  public logout() {
    setTimeout(() => {
      this._authService.logout();
    });
  }
}

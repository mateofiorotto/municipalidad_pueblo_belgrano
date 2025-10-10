import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgClass } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-header-admin',
  imports: [RouterModule, NgClass],
  templateUrl: './header-admin.component.html',
  styleUrl: './header-admin.component.css',
})
export class HeaderAdminComponent {
  protected _authService = inject(AuthService);
  public menuOpen: boolean = false;

  public logout() {
    setTimeout(() => {
      this._authService.logout();
    });
  }
  
  public toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }
}

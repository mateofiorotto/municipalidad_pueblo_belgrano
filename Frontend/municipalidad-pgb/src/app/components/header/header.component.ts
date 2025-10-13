import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgClass } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [RouterModule, NgClass],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
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

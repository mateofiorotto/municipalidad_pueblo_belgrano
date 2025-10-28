import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';
import { inject } from '@angular/core';

@Component({
  selector: 'app-dashboard.page',
  imports: [RouterLink],
  templateUrl: './dashboard.page.component.html',
  styleUrl: './dashboard.page.component.css'
})
export class DashboardPageComponent {
  protected _authService = inject(AuthService);
}

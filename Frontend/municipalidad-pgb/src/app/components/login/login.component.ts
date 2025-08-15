import { Component } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  public error: any = null;
  private _authService = inject(AuthService);
  private router = inject(Router);

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  public onSubmit(): void {
    const val = this.loginForm.value;

    if (val.username && val.password) {
      this._authService.login(val.username, val.password).subscribe({
        next: (response) => {
          const token = response.jwt;
          localStorage.setItem('auth_token', token);

          this.router.navigate(['admin/dashboard']).then(() => {
            Swal.fire({
              icon: 'success',
              title: '¡Bienvenido!',
              text: 'Has iniciado sesión correctamente',
              showConfirmButton: true,
              timer: 3500,
            });
          });
        },
        error: (err) => {
          if (err.status == 401) {
            Swal.fire({
              icon: 'error',
              title: 'Datos Incorrectos',
              text: 'El usuario o la contraseña son incorrectos',
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Ocurrió un error. Lo estamos solucionando.',
            });
          }
        },
      });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'El usuario o la contraseña son requeridos',
      });
    }
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}

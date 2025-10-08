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
  captchaToken: string | null = null;
  siteKey = '6LeVbbMrAAAAAB2NUZWTGVenSrwgi0afOlv6kPgi';
  public error: any = null;
  private _authService = inject(AuthService);
  private router = inject(Router);

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  public onSubmit(): void {
    const val = this.loginForm.value;

    if (val.username && val.password && this.captchaToken) {
      this._authService
        .login(val.username, val.password, this.captchaToken)
        .subscribe({
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
            window.grecaptcha.reset();
            this.captchaToken = null;
            if (err.status == 401) {
              Swal.fire({
                icon: 'error',
                title: 'Datos Incorrectos',
                text: 'El usuario o la contraseña son incorrectos',
              });
            } else if (err.status == 400) {
              window.grecaptcha.reset();
              this.captchaToken = null;
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: err.error.message,
              });
            } else if (err.status == 429) {
              window.grecaptcha.reset();
              this.captchaToken = null;
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Intentaste demasiadas veces. Por favor, intenta de nuevo en unos minutos.',
              });
            } else {
              window.grecaptcha.reset();
              this.captchaToken = null;
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Ocurrió un error. Lo estamos solucionando.',
              });
            }
          },
        });
    } else {
      window.grecaptcha.reset();
      this.captchaToken = null;
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'El usuario, la contraseña y el ReCaptcha son requeridos',
      });
    }
  }

  ngAfterViewInit() {
    const waitForGrecaptcha = () => {
      if (window.grecaptcha) {
        window.grecaptcha.render('captchaDiv', {
          sitekey: this.siteKey,
          callback: (token: string) => (this.captchaToken = token),
        });
      } else {
        setTimeout(waitForGrecaptcha, 100);
      }
    };
    waitForGrecaptcha();
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}

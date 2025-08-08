import { Component } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login.page',
  imports: [ReactiveFormsModule],
  templateUrl: './login.page.component.html',
  styleUrl: './login.page.component.css',
})
export class LoginPageComponent {

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
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.error = err.error;
      },
    });
  } else {
    this.error = {
      message: 'El usuario y la contraseña son requeridos',
    };
    
  }}

  get username(){
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}

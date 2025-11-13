import { Component } from '@angular/core';
import { TransparenciesService } from '../../../services/transparencies/transparencies.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';

@Component({
  selector: 'app-transparency-create',
  imports: [ReactiveFormsModule],
  templateUrl: './transparency-create.component.html',
  styleUrl: './transparency-create.component.css',
})
export class TransparencyCreateComponent {
  errors: { defaultMessage: string }[] = [];

  private _transparenciesService = inject(TransparenciesService);
  private _router = inject(Router);

  transparencyForm = new FormGroup({
    titulo: new FormControl('', [Validators.required]),
    pdf: new FormControl('', [Validators.required]),
    fecha: new FormControl('', [Validators.required]),
    tipo: new FormControl('', [Validators.required])
  });

  onSubmit(): void {
    const val = this.transparencyForm.value;

    if (val.pdf && val.fecha) {
      const transparencyRequest: any = val;

      this._transparenciesService.createTransparency(transparencyRequest).subscribe({
        next: (data) => {
          this.errors = [];
          this.transparencyForm.reset();

          this._router.navigate(['/admin/transparencias']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Transparencia creada',
              text: 'Transparencia creada correctamente',
              showConfirmButton: true,
            });
          });
        },
        error: (err) => {
          if (err.error && Array.isArray(err.error.errors)) {
            this.errors = err.error.errors;

            const listaErrores = `
              <ul style="text-align:left">
                ${this.errors
                  .map((e) => `<li>${e.defaultMessage}</li>`)
                  .join('')}
              </ul>
            `;

            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              html: listaErrores,
              showConfirmButton: true,
            });
          } else if (err.status == 400) {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: err.error.message,
              showConfirmButton: true,
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Ocurrio un error al crear la transparencia. Lo estamos solucionando.',
              showConfirmButton: true,
            });
          }
        },
      });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'Todos los campos son requeridos',
        showConfirmButton: true,
      });
    }
  }

  get titulo() {
    return this.transparencyForm.get('titulo');
  }

  get pdf() {
    return this.transparencyForm.get('pdf');
  }

  get fecha() {
    return this.transparencyForm.get('fecha');
  }

  get tipo() {
    return this.transparencyForm.get('tipo');
  }
}

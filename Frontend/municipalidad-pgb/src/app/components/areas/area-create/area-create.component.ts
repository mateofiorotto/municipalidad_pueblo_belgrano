import { Component } from '@angular/core';
import { AreasService } from '../../../services/areas/areas.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';

@Component({
  selector: 'app-area-create',
  imports: [ReactiveFormsModule],
  templateUrl: './area-create.component.html',
  styleUrl: './area-create.component.css'
})
export class AreaCreateComponent {
errors: { defaultMessage: string }[] = [];
  private _areasService = inject(AreasService);
  private _router = inject(Router);

  areaForm = new FormGroup({
    nombre: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100),
    ])
  });

  onSubmit(): void {
    const val = this.areaForm.value;

    if (
      val.nombre
    ) {
      const areaRequest: any = val;

      this._areasService.createArea(areaRequest).subscribe({
        next: (data) => {
          this.errors = [];
          this.areaForm.reset();

          this._router.navigate(['/admin/areas']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Área creada',
              text: 'Área creada correctamente',
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
          } else if (err.status == 409){
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
              text: 'Ocurrio un error al crear el área. Lo estamos solucionando.',
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

  get nombre() {
    return this.areaForm.get('nombre');
  }
}


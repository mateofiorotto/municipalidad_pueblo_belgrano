import { Component } from '@angular/core';
import { AreasService } from '../../../services/areas/areas.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { CategoriesService } from '../../../services/categories/categories.service';

@Component({
  selector: 'app-category-create',
  imports: [ReactiveFormsModule],
  templateUrl: './category-create.component.html',
  styleUrl: './category-create.component.css'
})
export class CategoryCreateComponent {
errors: { defaultMessage: string }[] = [];
  private _categoriesService = inject(CategoriesService);
  private _router = inject(Router);

  categoryForm = new FormGroup({
    nombre: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100),
    ])
  });

  onSubmit(): void {
    const val = this.categoryForm.value;

    if (
      val.nombre
    ) {
      const categoryRequest: any = val;

      this._categoriesService.createCategory(categoryRequest).subscribe({
        next: (data) => {
          this.errors = [];
          this.categoryForm.reset();

          this._router.navigate(['/admin/categorias']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Categoría creada',
              text: 'Categoría creada correctamente',
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
              text: 'Ocurrio un error al crear la categoría. Lo estamos solucionando.',
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
    return this.categoryForm.get('nombre');
  }
}

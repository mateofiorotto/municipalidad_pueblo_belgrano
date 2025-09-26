import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoriesService } from '../../../services/categories/categories.service';
import { CategoryResponseDTO } from '../../../models/category.model';

@Component({
  selector: 'app-category-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './category-edit.component.html',
  styleUrl: './category-edit.component.css'
})
export class CategoryEditComponent {
errors: { defaultMessage: string }[] = [];
  private _categoriesService = inject(CategoriesService);
  private _router = inject(Router);
  private _route = inject(ActivatedRoute);

  public category!: CategoryResponseDTO;

  categoryForm = new FormGroup({
    nombre: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100),
    ])
  });

  ngOnInit(): void {
    this.loadCategory();
  }

  onSubmit(): void {
    const val = this.categoryForm.value;

    if (
      val.nombre
    ) {
      const categoryRequest: any = val;

      this._categoriesService.updateCategory(categoryRequest, Number(this._route.snapshot.paramMap.get('id'))).subscribe({
        next: (data) => {
          this.errors = [];
          this.categoryForm.reset();

          this._router.navigate(['/admin/categorias']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Categoría editada',
              text: 'Categoría editada correctamente',
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
          } else if (err.status == 409) {
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
              text: 'Ocurrio un error al editar la categoría. Lo estamos solucionando.',
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

  public loadCategory(): void {
    const id = Number(this._route.snapshot.paramMap.get('id'));

    this._categoriesService.getCategoryById(id).subscribe({
      next: (data) => {
        this.category = data.result;

        this.categoryForm.patchValue({
          nombre: this.category.nombre
        });
      },
      error: (err) => {
        if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/admin/categorias']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar la categoría',
                showConfirmButton: true,
              });
            });
          }
      },
    });
  }

  get nombre() {
    return this.categoryForm.get('nombre');
  }
}

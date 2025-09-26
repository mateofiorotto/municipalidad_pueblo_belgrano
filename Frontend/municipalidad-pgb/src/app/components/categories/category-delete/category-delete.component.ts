import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CategoriesService } from '../../../services/categories/categories.service'; 
import { CategoryResponseDTO } from '../../../models/category.model';

@Component({
  selector: 'app-category-delete',
  imports: [],
  templateUrl: './category-delete.component.html',
  styleUrl: './category-delete.component.css'
})
export class CategoryDeleteComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _categoriesService = inject(CategoriesService);
  private categoryId!: number;

  public category!: CategoryResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._categoriesService.getCategoryById(+id).subscribe({
        next: (data) => {
          this.category = data.result;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar categoría o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }

  deleteCategory(): void {
    this.categoryId = this.category.id;

    this._categoriesService.deleteCategory(this.categoryId).subscribe({
      next: () => {
        this._router.navigate(['/admin/categorias']);
        Swal.fire({
          icon: 'success',
          title: 'Categoría Eliminada',
          text: 'Categoría eliminada correctamente',
          showConfirmButton: true,
        });
      },
      error: (err) => {
        if (err.status === 409) {
          this._router.navigate(['/admin/categorias']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'La categoría tiene noticias asociadas actualmente o fue asociada a una noticia vieja.',
              showConfirmButton: true,
            });
          });
        } else {
          this._router.navigate(['/admin/categorias']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Error al eliminar la categoría',
              showConfirmButton: true,
            });
          });
        }
      },
    });
  }

  cancel(): void {
    this._router.navigate(['/admin/noticias']);
  }
}

import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { CategoryResponseDTO } from '../../../models/category.model';
import { CategoriesService } from '../../../services/categories/categories.service';

@Component({
  selector: 'app-category-details',
  imports: [],
  templateUrl: './category-details.component.html',
  styleUrl: './category-details.component.css'
})
export class CategoryDetailsComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _categoriesService = inject(CategoriesService);

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
}

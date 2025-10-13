import { Component } from '@angular/core';
import { NewsListByCategoryComponent } from '../../components/news/news-list-by-category/news-list-by-category.component';
import { CategoryByIdResponse, CategoryResponseDTO } from '../../models/category.model';
import { CategoriesService } from '../../services/categories/categories.service';
import { inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-news-by-category.page',
  imports: [NewsListByCategoryComponent],
  templateUrl: './news-by-category.page.component.html',
  styleUrl: './news-by-category.page.component.css'
})
export class NewsByCategoryPageComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _categoriesService = inject(CategoriesService);

  category!: CategoryResponseDTO;
  public loading: boolean = true;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._categoriesService.getCategoryById(+id).subscribe({
        next: (data) => {
          this.category = data.result;

          this.loading = false;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar categoria',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }
}

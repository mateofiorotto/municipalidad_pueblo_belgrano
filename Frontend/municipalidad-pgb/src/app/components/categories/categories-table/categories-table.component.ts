import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { CategoriesService } from '../../../services/categories/categories.service';
import { CategoryResponseDTO, CategoryListPaginatedResponse} from '../../../models/category.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-categories-table',
  imports: [MatIconModule, MatPaginator, RouterLink],
  templateUrl: './categories-table.component.html',
  styleUrl: './categories-table.component.css',
})
export class CategoriesTableComponent {
  private _categoriesService = inject(CategoriesService);
  private _router = inject(Router);

  public categoriesList: CategoryResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

  private setCategoriesData(data: CategoryListPaginatedResponse): void {
    this.categoriesList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadcategories(this.pageIndex);
  }

  public loadcategories(page: number): void {
    this._categoriesService.getCategoriesListPaginated(page).subscribe({
      next: (data) => {
        this.setCategoriesData(data);
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar categorias',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;

    this.loadcategories(this.pageIndex);
  }
}

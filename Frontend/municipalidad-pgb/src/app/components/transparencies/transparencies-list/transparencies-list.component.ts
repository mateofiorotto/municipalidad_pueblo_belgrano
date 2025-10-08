import { Component } from '@angular/core';
import { TransparencyCardComponent } from '../transparency-card/transparency-card.component';
import { TransparenciesService } from '../../../services/transparencies/transparencies.service';
import { inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { TransparencyListResponse, TransparencyResponseDTO } from '../../../models/transparency.model';
import { LoaderComponent } from '../../loader/loader.component';

@Component({
  selector: 'app-transparencies-list',
  imports: [TransparencyCardComponent, CommonModule, MatPaginatorModule, LoaderComponent],
  templateUrl: './transparencies-list.component.html',
  styleUrl: './transparencies-list.component.css',
})
export class TransparenciesListComponent {
  private _transparenciesService = inject(TransparenciesService);
  private _router = inject(Router);

  public loading: boolean = true;
  public transparenciesList: TransparencyResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

  private setTransparenciesData(data: TransparencyListResponse): void {
    this.transparenciesList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadTransparencies(this.pageIndex);
  }

  public loadTransparencies(page: number): void {
    this._transparenciesService.getTransparenciesList(page).subscribe({
      next: (data) => {
        this.setTransparenciesData(data);

        this.loading = false;
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar transparencias',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;

    this.loadTransparencies(this.pageIndex);

    window.scrollTo(0, 100);
  }
}

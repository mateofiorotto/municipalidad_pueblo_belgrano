import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { TransparencyListResponse, TransparencyResponseDTO } from '../../../models/transparency.model';
import { TransparenciesService } from '../../../services/transparencies/transparencies.service';
import { DatePipe, TitleCasePipe } from '@angular/common';
@Component({
  selector: 'app-transparencies-table',
  imports: [MatIconModule, MatPaginator, RouterLink, TitleCasePipe, DatePipe],
  templateUrl: './transparencies-table.component.html',
  styleUrl: './transparencies-table.component.css',
})
export class TransparenciesTableComponent {
  private _transparenciesService = inject(TransparenciesService);
  private _router = inject(Router);

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
  }
}

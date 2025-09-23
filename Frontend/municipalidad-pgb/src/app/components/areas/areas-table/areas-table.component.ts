import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { AreasService } from '../../../services/areas/areas.service';
import { AreaResponseDTO, AreaListPaginatedResponse } from '../../../models/area.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-areas-table',
  imports: [MatIconModule, MatPaginator, RouterLink],
  templateUrl: './areas-table.component.html',
  styleUrl: './areas-table.component.css'
})
export class AreasTableComponent {
  private _areasService = inject(AreasService);
  private _router = inject(Router);

  public areasList: AreaResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

   private setAreasData(data: AreaListPaginatedResponse): void {
      this.areasList = data.result.content;
      this.pageIndex = data.result.page.number;
      this.totalElements = data.result.page.totalElements;
      this.error = null;
    }

  ngOnInit(): void {
    this.loadAreas(this.pageIndex);
  }

  public loadAreas(page: number): void {
    this._areasService.getAreasListPaginated(page).subscribe({
      next: (data) => {
        this.setAreasData(data);
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar areas',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    
    this.loadAreas(this.pageIndex);
  }
}


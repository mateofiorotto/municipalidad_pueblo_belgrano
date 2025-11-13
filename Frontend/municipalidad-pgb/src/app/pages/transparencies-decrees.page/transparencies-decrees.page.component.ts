import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { DatePipe, TitleCasePipe } from '@angular/common';
import { TransparenciesService } from '../../services/transparencies/transparencies.service';
import { TransparencyResponseDTO } from '../../models/transparency.model';
import { TransparencyListResponse } from '../../models/transparency.model';

@Component({
  selector: 'app-transparencies-decrees.page',
  imports: [DatePipe, TitleCasePipe, MatPaginator],
  templateUrl: './transparencies-decrees.page.component.html',
  styleUrl: './transparencies-decrees.page.component.css'
})
export class TransparenciesDecreesPageComponent implements OnInit {
private _transparenciesService = inject(TransparenciesService);
  private _router = inject(Router);

  public decretosList: TransparencyResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

  private setDecretosData(data: TransparencyListResponse): void {
    this.decretosList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadDecretos(this.pageIndex);
  }

  public loadDecretos(page: number): void {
    this._transparenciesService.getTransparenciesByType(page, 'DECRETOS').subscribe({
      next: (data) => {
        this.setDecretosData(data);
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar decretos',
            showConfirmButton: true,
          });

          console.log(err)
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.loadDecretos(this.pageIndex);
  }

  public openPdf(pdfUrl: string): void {
    window.open(pdfUrl, '_blank');
  }
}

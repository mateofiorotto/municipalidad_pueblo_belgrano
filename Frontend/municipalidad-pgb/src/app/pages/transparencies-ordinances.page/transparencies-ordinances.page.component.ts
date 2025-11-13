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
  templateUrl: './transparencies-ordinances.page.component.html',
  styleUrl: './transparencies-ordinances.page.component.css'
})
export class TransparenciesOrdinancesPageComponent implements OnInit {
private _transparenciesService = inject(TransparenciesService);
  private _router = inject(Router);

  public ordenanzasList: TransparencyResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

  private setOrdenanzasData(data: TransparencyListResponse): void {
    this.ordenanzasList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadOrdenanzas(this.pageIndex);
  }

  public loadOrdenanzas(page: number): void {
    this._transparenciesService.getTransparenciesByType(page, 'ORDENANZAS').subscribe({
      next: (data) => {
        this.setOrdenanzasData(data);
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar ordenanzas',
            showConfirmButton: true,
          });

          console.log(err)
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.loadOrdenanzas(this.pageIndex);
  }

  public openPdf(pdfUrl: string): void {
    window.open(pdfUrl, '_blank');
  }
}

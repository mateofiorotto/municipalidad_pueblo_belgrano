import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import {
  ComplaintResponseDTO,
  ComplaintListResponse,
} from '../../../models/complaint.model';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { AreaResponseDTO } from '../../../models/area.model';
import { AreasService } from '../../../services/areas/areas.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-complaints-table',
  imports: [MatPaginatorModule, MatIconModule, FormsModule],
  templateUrl: './complaints-table.component.html',
  styleUrl: './complaints-table.component.css',
})
export class ComplaintsTableComponent {
  private _complaintService = inject(ComplaintService);
  private _areasService = inject(AreasService);
  private _router = inject(Router);

  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;
  public complaintsList: ComplaintResponseDTO[] = [];
  public areasList: AreaResponseDTO[] = [];
  public selectedAreaId: number = 0;
  public selectedStatus: string = '';

  private setComplaintsData(data: ComplaintListResponse): void {
    this.complaintsList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadAreas();
    this.loadComplaints(this.pageIndex);
  }

  loadComplaints(page: number, status?: string): void {
    this._complaintService.getComplaintsList(page, status).subscribe({
      next: (data) => {
        this.setComplaintsData(data);
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar reclamos o no estas autorizado/a',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  loadAreas(): void {
    this._areasService.getAreasList().subscribe({
      next: (data) => {
        this.areasList = data.result;
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar areas o no estas autorizado/a',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;

    if (this.selectedAreaId && this.selectedAreaId !== 0) {
      this._complaintService
        .getComplaintsListByArea(this.pageIndex, this.selectedAreaId)
        .subscribe({
          next: (data) => {
            this.setComplaintsData(data);
          },
          error: (err) => {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar reclamos o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          },
        });
    } else if (this.selectedStatus) {
        this.loadComplaints(this.pageIndex, this.selectedStatus);
    } else {
        this.loadComplaints(this.pageIndex);
    }
  }

  onAreaChange(selectedValue: number): void {
    this.selectedAreaId = selectedValue;
    this.selectedStatus = '';
    this.pageIndex = 0;

    if (this.selectedAreaId == 0) {
      this.loadComplaints(this.pageIndex);
      return;
    }

    this._complaintService
      .getComplaintsListByArea(this.pageIndex, this.selectedAreaId)
      .subscribe({
        next: (data) => {
          this.setComplaintsData(data);
        },
        error: (err) => {
          this._router.navigate(['/']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Error al cargar reclamos o no estas autorizado/a',
              showConfirmButton: true,
            });
          });
        },
      });
  }

  onStatusChange(selectedValue: string): void {
  this.selectedStatus = selectedValue;
  this.selectedAreaId = 0;
  this.pageIndex = 0;
  this.loadComplaints(this.pageIndex, this.selectedStatus);
}
}
